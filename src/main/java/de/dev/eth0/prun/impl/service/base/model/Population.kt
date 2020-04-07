/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.base.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import kotlin.math.min


@ApiModel(value = "Population", description = "Population of a base")
data class Population @JsonCreator constructor(
    @ApiModelProperty("Required number", example = "100")
    @JsonProperty("required") val required: Int,
    @ApiModelProperty("Capacity", example = "200")
    @JsonProperty("capacity") val capacity: Int,
    @ApiModelProperty("Provide Luxury 1", example = "true")
    @JsonProperty("luxury1") val luxury1: Boolean,
    @ApiModelProperty("Provide Luxury 2", example = "true")
    @JsonProperty("luxury2") val luxury2: Boolean
) {
  @ApiModelProperty("Current workforce", example = "100")
  @JsonProperty("current")
  val current = min(required, capacity)

  @ApiModelProperty("Satisfaction of the population, value between 0 and 1", example = "0.9")
  @JsonProperty("satisfaction")
  val satisfaction = min(1, if (capacity > 0 && required > 0) capacity / required else 0)

  @ApiModelProperty("Efficiency of the population, value between 0 and 1", example = "0.87")
  @JsonProperty("efficiency")
  val efficiency = satisfaction * 0.79 + if (luxury1 && luxury2) 0.21 else if (luxury1 || luxury2) 0.08 else 0.0
}
