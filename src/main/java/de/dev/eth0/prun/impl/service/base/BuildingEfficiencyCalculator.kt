/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.base

import de.dev.eth0.prun.impl.model.Building
import de.dev.eth0.prun.impl.model.Planet
import de.dev.eth0.prun.impl.service.base.model.Base
import de.dev.eth0.prun.impl.service.base.model.CoGCBonus
import de.dev.eth0.prun.impl.service.base.model.Population
import de.dev.eth0.prun.impl.service.base.model.PopulationLevel
import de.dev.eth0.prun.impl.util.MathUtil

/**
 *
 */
class BuildingEfficiencyCalculator {

  /**
   * Bonus used to calculate the fertility bonus
   */
  private val fertilityBonusFactor = 0.3

  /**
   * Bonus for experts
   */
  private val expertBonus = mapOf(
      0 to 0.0,
      1 to 0.0306,
      2 to 0.0696,
      3 to 0.1248,
      4 to 0.1974,
      5 to 0.2840
  )

  /**
   * Return the efficiency of the requested building
   */
  fun calculateEfficiency(building: Building, base: Base, population: Map<PopulationLevel, Population>, planet: Planet): Double {
    // ignore any buildings without expertise (CM, HB1, etc)
    if (building.expertise == null) return 0.0

    var efficiency = getWorkforceEficiency(building, population, base.cogcBonus)
    efficiency += getCogcBonus(building, base.cogcBonus)
    efficiency += getExpertBonus(building, base)
    efficiency += getFertilityBonus(building, planet)
    return MathUtil.round(efficiency, 3)
  }

  /**
   * Workforce efficency is the sum of all worker efficiencies multiplied by the number of workers divided by number of workers
   * e.g. REF: (60 * Eff_pio + 20 * Eff_set) / 80
   *
   * If a cogc bonus for a group is set, it's added to the value:
   * e.g. REF: (60 * (Eff_pio + 10) + 20 * Eff_set) / 80
   */
  private fun getWorkforceEficiency(building: Building, population: Map<PopulationLevel, Population>, cogcBonus: CoGCBonus?): Double {
    val workforce = mapOf(
        PopulationLevel.PIONEERS to (building.pioneers ?: 0),
        PopulationLevel.SETTLERS to (building.settlers ?: 0),
        PopulationLevel.TECHNICIANS to (building.technicians ?: 0),
        PopulationLevel.ENGINEERS to (building.engineers ?: 0),
        PopulationLevel.SCIENTISTS to (building.scientists ?: 0)
    )
    val sumWorkforce = workforce.values.sum()

    val effectiveEfficiency = population.map {
      it.key to
          (it.value.efficiency + if (cogcBonus != null && cogcBonus.toString() == it.key.toString()) cogcBonus.bonus else 0.0)
    }.toMap()

    val efficiency = workforce.map { it.value * (effectiveEfficiency[it.key] ?: 0.0) }
    return efficiency.sum() / sumWorkforce
  }

  private fun getCogcBonus(building: Building, cocgBonus: CoGCBonus?): Double {
    if (cocgBonus != null && cocgBonus.toString() == building.expertise.toString()) {
      return cocgBonus.bonus
    }
    return 0.0
  }

  private fun getExpertBonus(building: Building, base: Base): Double {
    return base.experts.getOrDefault(building.expertise!!, 0).let { expertBonus.getOrDefault(it, 0.0) }
  }

  /**
   * Get the fertility bonus
   */
  private fun getFertilityBonus(building: Building, planet: Planet): Double {
    return when (building.id) {
      "FRM", "ORC" -> planet.fertility * fertilityBonusFactor
      else -> 0.0
    }
  }

}