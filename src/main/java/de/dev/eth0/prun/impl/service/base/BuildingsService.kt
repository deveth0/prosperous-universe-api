/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.base

import com.fasterxml.jackson.databind.ObjectMapper
import de.dev.eth0.prun.impl.FilesProperties
import de.dev.eth0.prun.impl.service.base.model.Building
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service

@Service
@EnableConfigurationProperties(FilesProperties::class)
class BuildingsService @Autowired constructor(filesProperties: FilesProperties, objectMapper: ObjectMapper) {

  val buildings: Map<String, Building>

  init {
    val parser = BuildingsParser(filesProperties.buildings, objectMapper)
    buildings = parser.buildings
  }

  fun getBuilding(ticker: String): Building? {
    return buildings[ticker]
  }

}