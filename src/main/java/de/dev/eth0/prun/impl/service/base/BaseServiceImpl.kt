/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.base

import de.dev.eth0.prun.impl.model.Building
import de.dev.eth0.prun.impl.model.Planet
import de.dev.eth0.prun.impl.model.PlanetaryResource
import de.dev.eth0.prun.impl.service.base.model.Base
import de.dev.eth0.prun.impl.service.base.model.BaseCalculation
import de.dev.eth0.prun.impl.service.base.model.BaseConsumptionSetting
import de.dev.eth0.prun.impl.service.base.model.BaseMaterials
import de.dev.eth0.prun.impl.service.base.model.Population
import de.dev.eth0.prun.impl.service.base.model.PopulationConsumption
import de.dev.eth0.prun.impl.service.base.model.PopulationLevel
import de.dev.eth0.prun.impl.service.base.model.RestoredBase
import de.dev.eth0.prun.service.BaseService
import de.dev.eth0.prun.service.DeeplinkService
import de.dev.eth0.prun.service.PlanetsService
import de.dev.eth0.prun.service.RecipeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.math.abs

@Service
class BaseServiceImpl @Autowired constructor(
  private val buildingsService: BuildingsService,
  private val recipeService: RecipeService,
  private val planetsService: PlanetsService,
  private val consumptionService: ConsumptionService,
  private val deeplinkService: DeeplinkService
) : BaseService {

  override fun restoreBase(deeplink: String): RestoredBase? {
    val base = deeplinkService.resolveDeeplink(deeplink)

    return RestoredBase(base, calculate(base))
  }

  override fun calculate(base: Base): BaseCalculation {
    val planet = planetsService.getPlanet(base.planet) ?: throw Exception("invalid planet")

    val buildings = base.buildings.mapKeys { (k, v) -> buildingsService.getBuilding(k).let { it } }.filterKeys { it != null } as Map<Building, Int>
    val area = buildings.keys.sumBy { it.area }

    val population = getPopulation(buildings, base.consumption)

    val consumables = consumptionService.calculateConsumption(population, base.consumption)

    val buildingEfficiencies = getBuildingEfficiencies(base, population, planet)

    val materials = getMaterials(base, planet, buildingEfficiencies, consumables)

    return BaseCalculation(area, population, consumables, buildingEfficiencies, materials, deeplinkService.createDeeplink(base))
  }

  private fun getBuildingEfficiencies(base: Base, population: Map<PopulationLevel, Population>, planet: Planet): Map<String, Double> {
    val efficiencyCalculator = BuildingEfficiencyCalculator()
    return base.buildings
      .mapNotNull { (k, v) -> buildingsService.getBuilding(k).let { it } }
      .filter { it.expertise != null }
      .map { building -> building.id to efficiencyCalculator.calculateEfficiency(building, base, population, planet) }.toMap()
  }

  private fun getMaterials(
    base: Base,
    planet: Planet,
    buildingEfficiencies: Map<String, Double>,
    consumables: Map<PopulationLevel, List<PopulationConsumption>>
  ): BaseMaterials {
    //TODO: throw exception on invalid recipes
    val recipes = base.recipes.mapNotNull { r -> recipeService.getRecipe(r.key)?.let { Pair(it, r.value) } }.toMap()
    val buildingNumber = base.buildings

    val calculator = ProductionCalculator()
    val materials = BaseMaterials()

    // production recipes
    for ((recipe, amount) in recipes) {
      val efficiency = buildingEfficiencies[recipe.buildingId]
      if (efficiency != null && efficiency > 0 && buildingNumber.containsKey(recipe.buildingId)) {
        val factor = buildingNumber[recipe.buildingId]!! * amount
        materials.add(BaseMaterials.multiply(calculator.calculateProduction(recipe, efficiency), factor))
      }
    }
    // also add the resource extraction
    for ((material, amount) in base.extraction) {
      val planetaryResource = planet.resources[material]
      if (planetaryResource != null) {
        val buildingId = when (planetaryResource.form) {
          PlanetaryResource.Form.ATMOSPHERIC -> "COL"
          PlanetaryResource.Form.LIQUID -> "RIG"
          PlanetaryResource.Form.MINERAL -> "EXT"
        }
        val efficiency = buildingEfficiencies[buildingId]
        if (efficiency != null && efficiency > 0 && buildingNumber.containsKey(buildingId)) {
          val factor = buildingNumber[buildingId]!! * amount
          materials.add(BaseMaterials.multiply(calculator.calculateExtraction(planetaryResource, efficiency), factor))
        }
      }
    }
    // add the consumables of the population
    for ((_, consume) in consumables) {
      materials.add(BaseMaterials(input = consume.map { it.ticker to it.amount }.toMap().toMutableMap()))
    }
    return materials
  }

  private fun getPopulation(
    buildings: Map<Building, Int>,
    consumptionSettings: Map<PopulationLevel, BaseConsumptionSetting>
  ): Map<PopulationLevel, Population> {
    val pioneers = getPioneerPopulationEntry(buildings, consumptionSettings)
    val settlers = getSettlerPopulationEntry(buildings, consumptionSettings)
    val technicians = getTechnicianPopulationEntry(buildings, consumptionSettings)
    val engineers = getEngineerPopulationEntry(buildings, consumptionSettings)
    val scientists = getScientistsPopulationEntry(buildings, consumptionSettings)
    return mapOf(
      PopulationLevel.PIONEERS to pioneers,
      PopulationLevel.SETTLERS to settlers,
      PopulationLevel.TECHNICIANS to technicians,
      PopulationLevel.ENGINEERS to engineers,
      PopulationLevel.SCIENTISTS to scientists
    ).filterValues { it.required != 0 || it.capacity != 0 }
  }

  private fun getPioneerPopulationEntry(buildings: Map<Building, Int>, consumptionSettings: Map<PopulationLevel, BaseConsumptionSetting>): Population {
    val filteredBuildings = buildings.filter { it.key.pioneers != null }.mapKeys { it.key.pioneers }
    return getPopulationEntry(filteredBuildings as Map<Int, Int>, PopulationLevel.PIONEERS, consumptionSettings)
  }

  private fun getSettlerPopulationEntry(buildings: Map<Building, Int>, consumptionSettings: Map<PopulationLevel, BaseConsumptionSetting>): Population {
    val filteredBuildings = buildings.filter { it.key.settlers != null }.mapKeys { it.key.settlers }
    return getPopulationEntry(filteredBuildings as Map<Int, Int>, PopulationLevel.SETTLERS, consumptionSettings)
  }

  private fun getTechnicianPopulationEntry(buildings: Map<Building, Int>, consumptionSettings: Map<PopulationLevel, BaseConsumptionSetting>): Population {
    val filteredBuildings = buildings.filter { it.key.technicians != null }.mapKeys { it.key.technicians }
    return getPopulationEntry(filteredBuildings as Map<Int, Int>, PopulationLevel.TECHNICIANS, consumptionSettings)
  }

  private fun getEngineerPopulationEntry(buildings: Map<Building, Int>, consumptionSettings: Map<PopulationLevel, BaseConsumptionSetting>): Population {
    val filteredBuildings = buildings.filter { it.key.engineers != null }.mapKeys { it.key.engineers }
    return getPopulationEntry(filteredBuildings as Map<Int, Int>, PopulationLevel.ENGINEERS, consumptionSettings)
  }

  private fun getScientistsPopulationEntry(buildings: Map<Building, Int>, consumptionSettings: Map<PopulationLevel, BaseConsumptionSetting>): Population {
    val filteredBuildings = buildings.filter { it.key.scientists != null }.mapKeys { it.key.scientists }
    return getPopulationEntry(filteredBuildings as Map<Int, Int>, PopulationLevel.SCIENTISTS, consumptionSettings)
  }

  private fun getPopulationEntry(
    buildings: Map<Int, Int>,
    populationLevel: PopulationLevel,
    consumptionSettings: Map<PopulationLevel, BaseConsumptionSetting>
  ): Population {
    val consumptionSetting = consumptionSettings.getOrDefault(populationLevel, BaseConsumptionSetting(false, false))
    val required = buildings.filter { it.key > 0 }.entries.sumBy { it.key * it.value }
    val available = abs(buildings.filter { it.key < 0 }.entries.sumBy { it.key * it.value })
    return Population(required, available, consumptionSetting.luxury1, consumptionSetting.luxury2)
  }
}