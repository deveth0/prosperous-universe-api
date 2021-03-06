/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.base

import com.fasterxml.jackson.databind.ObjectMapper
import de.dev.eth0.prun.impl.FilesProperties
import de.dev.eth0.prun.impl.model.Building
import de.dev.eth0.prun.service.RecipeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
@EnableConfigurationProperties(FilesProperties::class)
open class BuildingsService
@Autowired constructor(filesProperties: FilesProperties,
                       objectMapper: ObjectMapper,
                       private val recipeService: RecipeService) {

  private val buildings: Map<String, Building>

  init {
    val parser = BuildingsParser(filesProperties.buildings, objectMapper)
    buildings = parser.buildings
  }

  @Cacheable("buildings")
  open fun getBuildings(): Map<String, Building> {
    return buildings
  }

  @Cacheable("building")
  open fun getBuilding(ticker: String): Building? {
    val ret = buildings[ticker] ?: return null
    ret.recipes = recipeService.getRecipes(ret.id)
    return ret
  }

}