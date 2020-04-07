/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.recipe

import de.dev.eth0.prun.impl.FilesProperties
import de.dev.eth0.prun.impl.model.Recipe
import de.dev.eth0.prun.service.RecipeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
open class RecipeServiceImpl @Autowired constructor(filesProperties: FilesProperties) : RecipeService {

  private val recipes: Map<String, List<Recipe>>

  init {
    val parser = RecipeParser(filesProperties.recipes, filesProperties.recipesInput, filesProperties.recipesOutput)
    recipes = parser.recipesByBuilding
  }

  @Cacheable("recipes")
  override fun getRecipes(): List<Recipe> {
    return recipes.values.flatten().toList()
  }

  override fun getRecipes(buildingId: String): List<Recipe> {
    return recipes[buildingId].orEmpty()
  }
}