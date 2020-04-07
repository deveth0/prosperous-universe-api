/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.base.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(value = "Consumable", description = "Info on a consumable")
data class Consumable @JsonCreator constructor(
    @ApiModelProperty(value = "Unique Id of the consumable", example = "DW")
    @JsonProperty("id") val id: String,
    @ApiModelProperty(value = "Info if the consumable is essential", example = "true")
    @JsonProperty("id") val essential: Boolean
)