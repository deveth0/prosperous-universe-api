/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.base

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import de.dev.eth0.prun.impl.service.base.model.PopulationConsumption
import de.dev.eth0.prun.impl.service.base.model.PopulationLevel
import org.springframework.core.io.ClassPathResource

/**
 * Parser for consumption related files
 */
class ConsumptionParser constructor(consumableFile: String, objectMapper: ObjectMapper) {

  val consumptions: Map<PopulationLevel, List<PopulationConsumption>>

  init {
    consumptions = objectMapper.readValue(ClassPathResource(consumableFile).file, object : TypeReference<Map<PopulationLevel, List<PopulationConsumption>>>() {})
  }


}