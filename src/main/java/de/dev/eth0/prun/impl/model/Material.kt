/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty


@ApiModel("Material", description = "Material used for manufacturing and trading")
data class Material(

    @ApiModelProperty("Unique Id of the Material", example = "H2O")
    @JsonProperty("ticker") val ticker: String,
    @ApiModelProperty("Name of the Material", example = "Water")
    @JsonProperty("name") val name: String,
    @ApiModelProperty("Category of the Material", example = "Agricultural")
    @JsonProperty("category") val category: String,
    @ApiModelProperty("Weight of the Material", example = "1")
    @JsonProperty("weight") val weight: Float,
    @ApiModelProperty("Volume of the Material", example = "1.4")
    @JsonProperty("volume") val volume: Float,
    @ApiModelProperty("True if the Material is a natural one", example = "true")
    @JsonProperty("natural") val natural: Boolean


)