/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.base.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("Configuration on the consumption of the base")
data class BaseConsumptionSetting @JsonCreator constructor(
    @ApiModelProperty("Enable Luxury 1", example = "true")
    @JsonProperty("luxury1") val luxury1: Boolean,

    @ApiModelProperty("Enable Luxury 2", example = "true")
    @JsonProperty("luxury2") val luxury2: Boolean
)