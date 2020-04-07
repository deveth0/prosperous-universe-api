/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.base.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(value = "BaseCalculation", description = "Calculation on a base")
data class BaseCalculation @JsonCreator constructor(
    @ApiModelProperty(value = "Used area")
    @JsonProperty("area") val area: Int,

    @ApiModelProperty(value = "Population of the base")
    @JsonProperty("population") val population: Map<PopulationLevel, Population>,

    @ApiModelProperty(value = "Consumption of the base")
    @JsonProperty("consumption") val consumption: Map<PopulationLevel, List<PopulationConsumption>>,

    @ApiModelProperty("Material input/output of the base")
    @JsonProperty("materials") val materials: Map<String, Double>


)