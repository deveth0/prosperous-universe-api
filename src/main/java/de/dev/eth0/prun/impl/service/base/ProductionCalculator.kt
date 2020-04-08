/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.base

import de.dev.eth0.prun.impl.model.Recipe
import de.dev.eth0.prun.impl.util.MathUtil

/**
 * Calculate the production (input / output materials)
 */
class ProductionCalculator {


  /**
   * Calculate the resource input / output for the given recipe / efficiency
   */
  fun calculateProduction(recipe: Recipe, efficiency: Double): Map<String, Double> {

    val queuePerDay = MathUtil.round((86400 * efficiency / recipe.duration), 2)

    return recipe.inputs.map { it.key to MathUtil.round(it.value * queuePerDay * -1, 2) }
        .plus(recipe.outputs.map { it.key to MathUtil.round(it.value * queuePerDay, 2) }).toMap()

  }

}