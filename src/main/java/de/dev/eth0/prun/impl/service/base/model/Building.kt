/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.base.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(value = "Building", description = "Info on a building")
data class Building @JsonCreator constructor(
    @ApiModelProperty(value = "Unique Id of the Building", example = "FF")
    @JsonProperty("id") val id: String,
    @ApiModelProperty(value = "Name of the Building", example = "Fermenter")
    @JsonProperty("name") val name: String,
    @ApiModelProperty(value = "Area used by the Building", example = "25")
    @JsonProperty("area") val area: Int,
    @ApiModelProperty(value = "Expert Expertise required by the building", example = "FOOD_INDUSTRIES")
    @JsonProperty("expertise", required = false) val expertise: Expertise?,
    @ApiModelProperty(value = "Number of Pioneer Workers", example = "50")
    @JsonProperty("pioneers", required = false) val pioneers: Int?,
    @ApiModelProperty(value = "Number of Settler Workers", example = "50")
    @JsonProperty("settlers", required = false) val settlers: Int?,
    @ApiModelProperty(value = "Number of Technicians Workers", example = "20")
    @JsonProperty("technicians", required = false) val technicians: Int?,
    @ApiModelProperty(value = "Number of Engineer Workers", example = "20")
    @JsonProperty("engineers", required = false) val engineers: Int?,
    @ApiModelProperty(value = "Number of Scientist Workers", example = "50")
    @JsonProperty("scientists", required = false) val scientists: Int?
) {
  enum class Expertise {
    FOOD_INDUSTRIES, RESOURCE_EXTRACTION, CHEMISTRY, AGRICULTURE, MANUFACTURING, ELECTRONICS, METALLURGY, FUEL_REFINING, CONSTRUCTION
  }
}