/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "PlanetaryResource", description = "Details on a planetary resource")
data class PlanetaryResource @JsonCreator constructor(
    @ApiModelProperty(value = "Unique Id of the Resource", example = "H2O")
    @JsonProperty("resourceId") val resourceId: String,
    @ApiModelProperty(value = "Form of the Resource", example = "Atmospheric")
    @JsonProperty("form") val form: PlanetaryResource.Form,
    @ApiModelProperty(value = "Concentration of the Resource", example = "0.21")
    @JsonProperty("concentration") val concentration: Double
) {
  enum class Form {
    ATMOSPHERIC, LIQUID, MINERAL
  }
}