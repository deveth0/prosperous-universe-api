/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.base

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import de.dev.eth0.prun.impl.model.Building
import org.springframework.core.io.ClassPathResource

/**
 * Parser for building related files
 */
class BuildingsParser constructor(buildingsFile: String, objectMapper: ObjectMapper) {

  val buildings: Map<String, Building>

  init {
    val allBuildings = objectMapper.readValue(ClassPathResource(buildingsFile).file, object : TypeReference<Map<String, Building>>() {})
    buildings = allBuildings
  }


}