/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.base

import com.fasterxml.jackson.databind.ObjectMapper
import de.dev.eth0.prun.impl.FilesProperties
import de.dev.eth0.prun.impl.service.base.model.BaseConsumptionSetting
import de.dev.eth0.prun.impl.service.base.model.Population
import de.dev.eth0.prun.impl.service.base.model.PopulationConsumption
import de.dev.eth0.prun.impl.service.base.model.PopulationLevel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConsumptionService @Autowired constructor(filesProperties: FilesProperties, objectMapper: ObjectMapper) {

  val consumptions: Map<PopulationLevel, List<PopulationConsumption>>

  init {
    val parser = ConsumptionParser(filesProperties.consumption, objectMapper)
    consumptions = parser.consumptions
  }


  /**
   * Calculate the Consumables for the given workforce
   */
  fun calculateConsumption(population: Map<PopulationLevel, Population>, consumptionSettings: Map<PopulationLevel, BaseConsumptionSetting>): Map<PopulationLevel, List<PopulationConsumption>> {
    return population.filterValues { it.current > 0 }.map { it.key to calculateConsumption(it.key, consumptionSettings[it.key], it.value.current) }.toMap()
  }

  private fun calculateConsumption(level: PopulationLevel, baseConsumptionSetting: BaseConsumptionSetting?, number: Int): List<PopulationConsumption> {
    val consumptionForLevel = consumptions[level] ?: return listOf()
    val essentialConsumptions = consumptionForLevel.filter { it.essential }

    var luxuryConsumptions = consumptionForLevel.filter { !it.essential }
    if (baseConsumptionSetting != null) {
      if (!baseConsumptionSetting.luxury2) luxuryConsumptions = luxuryConsumptions.dropLast(1)
      if (!baseConsumptionSetting.luxury1) luxuryConsumptions = luxuryConsumptions.drop(1)
    }

    return essentialConsumptions.plus(luxuryConsumptions).map { PopulationConsumption(it.ticker, (it.amount * number) / 100, it.essential) }
  }

}