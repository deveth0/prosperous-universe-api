/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.base

import de.dev.eth0.prun.impl.model.Building
import de.dev.eth0.prun.impl.service.base.model.*
import de.dev.eth0.prun.service.BaseService
import de.dev.eth0.prun.service.RecipeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.math.abs

@Service
class BaseServiceImpl @Autowired constructor(
    private val buildingsService: BuildingsService,
    private val recipeService: RecipeService,
    private val consumptionService: ConsumptionService) : BaseService {

  override fun calculate(base: Base): BaseCalculation {
    val buildings = base.buildings.mapNotNull { buildingsService.getBuilding(it) }
    val area = buildings.sumBy { it.area }


    val population = getPopulation(buildings, base.consumption);

    val consumables = consumptionService.calculateConsumption(population, base.consumption)

    val materials = getMaterials(base, population)
    return BaseCalculation(area, population, consumables, materials)
  }

  private fun getMaterials(base: Base, population: Map<PopulationLevel, Population>): Map<String, Double> {
    val recipes = base.recipes.mapNotNull { r -> recipeService.getRecipe(r.key)?.let { Pair(it, r.value) } }.toMap()

    val materials = mutableListOf<Pair<String, Double>>()
    for (recipe in recipes) {
      recipe.key.inputs.forEach { materials.add(Pair(it.key, -1 * it.value * 1.0)) }
      recipe.key.outputs.forEach { materials.add(Pair(it.key, it.value * 1.0)) }
    }
    return materials.groupBy { it.first }.mapValues { it.value.sumByDouble { pair -> pair.second } }
  }

  private fun getPopulation(buildings: List<Building>, consumptionSettings: Map<PopulationLevel, BaseConsumptionSetting>): Map<PopulationLevel, Population> {
    val pioneers = getPioneerPopulationEntry(buildings, consumptionSettings)
    val settlers = getSettlerPopulationEntry(buildings, consumptionSettings)
    val technicians = getTechnicianPopulationEntry(buildings, consumptionSettings)
    val engineers = getEngineerPopulationEntry(buildings, consumptionSettings)
    val scientists = getScientistsPopulationEntry(buildings, consumptionSettings)
    return mapOf(PopulationLevel.PIONEERS to pioneers,
        PopulationLevel.SETTLERS to settlers,
        PopulationLevel.TECHNICIANS to technicians,
        PopulationLevel.ENGINEERS to engineers,
        PopulationLevel.SCIENTISTS to scientists
    ).filterValues { it.required != 0 || it.capacity != 0 }
  }

  private fun getPioneerPopulationEntry(buildings: List<Building>, consumptionSettings: Map<PopulationLevel, BaseConsumptionSetting>): Population {
    val filteredBuildings = buildings.filter { it.pioneers != null }
    val consumptionSetting = consumptionSettings.getOrDefault(PopulationLevel.PIONEERS, BaseConsumptionSetting(false, false))
    val required = filteredBuildings.filter { it.pioneers!! > 0 }.sumBy { it.pioneers!! }
    val available = abs(filteredBuildings.filter { it.pioneers!! < 0 }.sumBy { it.pioneers!! })
    return Population(required, available, consumptionSetting.luxury1, consumptionSetting.luxury2)
  }

  private fun getSettlerPopulationEntry(buildings: List<Building>, consumptionSettings: Map<PopulationLevel, BaseConsumptionSetting>): Population {
    val filteredBuildings = buildings.filter { it.settlers != null }
    val consumptionSetting = consumptionSettings.getOrDefault(PopulationLevel.SETTLERS, BaseConsumptionSetting(false, false))
    val required = filteredBuildings.filter { it.settlers!! > 0 }.sumBy { it.settlers!! }
    val available = abs(filteredBuildings.filter { it.settlers!! < 0 }.sumBy { it.settlers!! })
    return Population(required, available, consumptionSetting.luxury1, consumptionSetting.luxury2)
  }

  private fun getTechnicianPopulationEntry(buildings: List<Building>, consumptionSettings: Map<PopulationLevel, BaseConsumptionSetting>): Population {
    val filteredBuildings = buildings.filter { it.technicians != null }
    val consumptionSetting = consumptionSettings.getOrDefault(PopulationLevel.TECHNICIANS, BaseConsumptionSetting(false, false))
    val required = filteredBuildings.filter { it.technicians!! > 0 }.sumBy { it.technicians!! }
    val available = abs(filteredBuildings.filter { it.technicians!! < 0 }.sumBy { it.technicians!! })
    return Population(required, available, consumptionSetting.luxury1, consumptionSetting.luxury2)
  }

  private fun getEngineerPopulationEntry(buildings: List<Building>, consumptionSettings: Map<PopulationLevel, BaseConsumptionSetting>): Population {
    val filteredBuildings = buildings.filter { it.engineers != null }
    val consumptionSetting = consumptionSettings.getOrDefault(PopulationLevel.ENGINEERS, BaseConsumptionSetting(false, false))
    val required = filteredBuildings.filter { it.engineers!! > 0 }.sumBy { it.engineers!! }
    val available = abs(filteredBuildings.filter { it.engineers!! < 0 }.sumBy { it.engineers!! })
    return Population(required, available, consumptionSetting.luxury1, consumptionSetting.luxury2)
  }

  private fun getScientistsPopulationEntry(buildings: List<Building>, consumptionSettings: Map<PopulationLevel, BaseConsumptionSetting>): Population {
    val filteredBuildings = buildings.filter { it.scientists != null }
    val consumptionSetting = consumptionSettings.getOrDefault(PopulationLevel.SCIENTISTS, BaseConsumptionSetting(false, false))
    val required = filteredBuildings.filter { it.scientists!! > 0 }.sumBy { it.scientists!! }
    val available = abs(filteredBuildings.filter { it.scientists!! < 0 }.sumBy { it.scientists!! })
    return Population(required, available, consumptionSetting.luxury1, consumptionSetting.luxury2)
  }

}