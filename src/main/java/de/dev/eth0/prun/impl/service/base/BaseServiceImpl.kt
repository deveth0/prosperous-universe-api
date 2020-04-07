/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.base

import de.dev.eth0.prun.impl.service.base.model.*
import de.dev.eth0.prun.service.BaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.math.abs

@Service
class BaseServiceImpl @Autowired constructor(private val buildingsService: BuildingsService, private val consumptionService: ConsumptionService) : BaseService {

  override fun calculate(base: Base): BaseCalculation {
    val buildings = base.buildings.mapNotNull { buildingsService.getBuilding(it) }
    val area = buildings.sumBy { it.area }


    val population = getPopulation(buildings);

    val consumables = consumptionService.calculateConsumption(population)
    return BaseCalculation(area, population, consumables)
  }

  private fun getPopulation(buildings: List<Building>): Map<PopulationLevel, Population> {
    val pioneers = getPioneerPopulationEntry(buildings)
    val settlers = getSettlerPopulationEntry(buildings)
    val technicians = getTechnicianPopulationEntry(buildings)
    val engineers = getEngineerPopulationEntry(buildings)
    val scientists = getScientistsPopulationEntry(buildings)
    return mapOf(PopulationLevel.PIONEERS to pioneers,
        PopulationLevel.SETTLERS to settlers,
        PopulationLevel.TECHNICIANS to technicians,
        PopulationLevel.ENGINEERS to engineers,
        PopulationLevel.SCIENTISTS to scientists
    ).filterValues { it.required != 0 && it.capacity != 0 }
  }

  private fun getPioneerPopulationEntry(buildings: List<Building>): Population {
    val filteredBuildings = buildings.filter { it.pioneers != null }
    val required = filteredBuildings.filter { it.pioneers!! > 0 }.sumBy { it.pioneers!! }
    val available = abs(filteredBuildings.filter { it.pioneers!! < 0 }.sumBy { it.pioneers!! })
    return Population(required, available)
  }

  private fun getSettlerPopulationEntry(buildings: List<Building>): Population {
    val filteredBuildings = buildings.filter { it.settlers != null }
    val required = filteredBuildings.filter { it.settlers!! > 0 }.sumBy { it.settlers!! }
    val available = abs(filteredBuildings.filter { it.settlers!! < 0 }.sumBy { it.settlers!! })
    return Population(required, available)
  }

  private fun getTechnicianPopulationEntry(buildings: List<Building>): Population {
    val filteredBuildings = buildings.filter { it.technicians != null }
    val required = filteredBuildings.filter { it.technicians!! > 0 }.sumBy { it.technicians!! }
    val available = abs(filteredBuildings.filter { it.technicians!! < 0 }.sumBy { it.technicians!! })
    return Population(required, available)
  }

  private fun getEngineerPopulationEntry(buildings: List<Building>): Population {
    val filteredBuildings = buildings.filter { it.engineers != null }
    val required = filteredBuildings.filter { it.engineers!! > 0 }.sumBy { it.engineers!! }
    val available = abs(filteredBuildings.filter { it.engineers!! < 0 }.sumBy { it.engineers!! })
    return Population(required, available)
  }

  private fun getScientistsPopulationEntry(buildings: List<Building>): Population {
    val filteredBuildings = buildings.filter { it.scientists != null }
    val required = filteredBuildings.filter { it.scientists!! > 0 }.sumBy { it.scientists!! }
    val available = abs(filteredBuildings.filter { it.scientists!! < 0 }.sumBy { it.scientists!! })
    return Population(required, available)
  }

}