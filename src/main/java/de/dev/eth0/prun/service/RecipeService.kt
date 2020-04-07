/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.service

import de.dev.eth0.prun.impl.model.Recipe

/**
 * Service which provides access to recipes
 */
interface RecipeService {

  /**
   * Get the requested recipe
   */
  fun getRecipe(recipeId: String): Recipe?

  /**
   * Get all available recipes
   */
  fun getRecipes(): List<Recipe>

  /**
   * Get all recipes for the given building
   */
  fun getRecipes(buildingId: String): List<Recipe>
}