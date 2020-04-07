/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.recipe

import de.dev.eth0.prun.impl.model.Recipe
import de.dev.eth0.prun.impl.service.recipe.model.CSVRecipe
import de.dev.eth0.prun.impl.service.recipe.model.CSVRecipeInputOutput
import de.dev.eth0.prun.impl.util.CsvUtil
import org.slf4j.LoggerFactory

/**
 * Parser for the materials
 */
class RecipeParser constructor(recipesFile: String, recipesInputFile: String, recipesOutputFile: String) {

  val recipesByBuilding: Map<String, List<Recipe>>
  private val logger = LoggerFactory.getLogger(RecipeParser::class.java.simpleName)

  init {
    val csvRecipes = CsvUtil.loadObjectList(CSVRecipe::class.java, recipesFile)
    val csvRecipeInputs = CsvUtil.loadObjectList(CSVRecipeInputOutput::class.java, recipesInputFile)
    val csvRecipeOutputs = CsvUtil.loadObjectList(CSVRecipeInputOutput::class.java, recipesOutputFile)

    recipesByBuilding = csvRecipes.map {
      Recipe(it.key,
          it.building,
          it.duration,
          findCSVRecipeInputOutput(csvRecipeInputs, it.key),
          findCSVRecipeInputOutput(csvRecipeOutputs, it.key))
    }.groupBy { it.buildingId }.toMap()
  }

  private fun findCSVRecipeInputOutput(inputOutputs: List<CSVRecipeInputOutput>, key: String): Map<String, Int> {
    return inputOutputs.filter { it.key == key }.map { it.material to it.amount }.toMap()
  }

}