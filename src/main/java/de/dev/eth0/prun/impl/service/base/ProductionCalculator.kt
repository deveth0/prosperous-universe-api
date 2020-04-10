/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.base

import de.dev.eth0.prun.impl.model.PlanetaryResource
import de.dev.eth0.prun.impl.model.Recipe
import de.dev.eth0.prun.impl.service.base.model.BaseMaterials
import de.dev.eth0.prun.impl.util.MathUtil

/**
 * Calculate the production (input / output materials)
 */
class ProductionCalculator {

  /**
   * Calculate the resource input / output for the given recipe / efficiency
   */
  fun calculateProduction(recipe: Recipe, efficiency: Double): BaseMaterials {

    val queuePerDay = MathUtil.round((86400 * efficiency / recipe.duration), 2)
    val input = recipe.inputs.map { it.key to it.value * queuePerDay }.toMap().toMutableMap()
    val output = recipe.outputs.map { it.key to it.value * queuePerDay }.toMap().toMutableMap()
    return BaseMaterials(input, output)
  }

  /**
   * Calculate the extraction of a resource
   */
  fun calculateExtraction(planetaryResource: PlanetaryResource, efficiency: Double): BaseMaterials {
    val rateMultiplicator = when (planetaryResource.form) {
      PlanetaryResource.Form.ATMOSPHERIC -> 60
      PlanetaryResource.Form.LIQUID -> 70
      PlanetaryResource.Form.MINERAL -> 70
    }
    return BaseMaterials(output = mutableMapOf(planetaryResource.resourceId to planetaryResource.concentration * rateMultiplicator * efficiency))
  }
}