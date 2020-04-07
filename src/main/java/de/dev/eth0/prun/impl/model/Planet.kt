/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Planet @JsonCreator constructor(
    @JsonProperty("id") val id: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("system") val system: String,
    @JsonProperty("fertility") val fertility: Double,
    @JsonProperty("gravity") val gravity: Double,
    @JsonProperty("plots") val plots: Int,
    @JsonProperty("pressure") val pressure: Double,
    @JsonProperty("temperature") val temperature: Double,
    @JsonProperty("type") val type: String,
    @JsonProperty("gravityLevel") val gravityLevel: Level,
    @JsonProperty("pressureLevel") val pressureLevel: Level,
    @JsonProperty("temperatureLevel") val temperatureLevel: Level,
    @JsonProperty("tier") val tier: Int,
    @JsonProperty("resources") val resources: Map<String, PlanetaryResource>
) {
  enum class Level { HIGH, LOW, NORMAL }
}

