/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.base.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import de.dev.eth0.prun.impl.model.Building
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty


@ApiModel(value = "Base", description = "Definition on a base")
data class Base @JsonCreator constructor(
    @ApiModelProperty(value = "Planet of this base", example = "WN-506a")
    @JsonProperty("planet") val planet: String,

    @ApiModelProperty(value = "Buildings in this base")
    @JsonProperty("buildings") val buildings: List<String>,

    @ApiModelProperty("Extraction of materials")
    @JsonProperty("extraction") val extraction: Map<String, Double> = mapOf(),

    @ApiModelProperty(value = "Consumption-Settings for this base")
    @JsonProperty("consumption") val consumption: Map<PopulationLevel, BaseConsumptionSetting> = mapOf(),

    @ApiModelProperty(value = "Recipes for this base and the percentage usage (0..1)")
    @JsonProperty("recipes") val recipes: Map<String, Double>,

    @ApiModelProperty("Experts")
    @JsonProperty("experts") val experts: Map<Building.Expertise, Int> = mapOf(),

    @ApiModelProperty("Chamber of Global Commerce Bonus")
    @JsonProperty("cogcBonus") val cogcBonus: CoGCBonus?

)