/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel

@ApiModel("Recipe", description = "Definition of a recipe")
data class Recipe(
    @JsonProperty("id") val id: String,
    @JsonProperty("buildingId") val buildingId: String,
    @JsonProperty("duration") val duration: Int,
    @JsonProperty("inputs") val inputs: Map<String, Int>,
    @JsonProperty("outputs") val outputs: Map<String, Int>
)