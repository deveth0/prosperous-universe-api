/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class PlanetaryResource @JsonCreator constructor(
    @JsonProperty("resourceId") val resourceId: String,
    @JsonProperty("form") val form: String,
    @JsonProperty("concentration") val concentration: Double
)