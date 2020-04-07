/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.base.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty


@ApiModel(value = "Base", description = "Definition on a base")
data class Base @JsonCreator constructor(
    @ApiModelProperty(value = "Buildings in this base")
    @JsonProperty("buildings") val buildings: List<String>,
    @ApiModelProperty(value = "Consumption-Settings for this base")
    @JsonProperty("consumption") val consumption: Map<PopulationLevel, BaseConsumptionSetting>

)