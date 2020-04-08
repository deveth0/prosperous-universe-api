/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.planets.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import de.dev.eth0.prun.impl.model.Planet

@JsonIgnoreProperties(ignoreUnknown = true)
data class CSVPlanet @JsonCreator constructor(
    @JsonProperty("id") val id: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("system") val system: String,
    @JsonProperty("fertility") val fertility: Double,
    @JsonProperty("gravity") val gravity: Double,
    @JsonProperty("plots") val plots: Int,
    @JsonProperty("pressure") val pressure: Double,
    @JsonProperty("temperature") val temperature: Double,
    @JsonProperty("type") val type: String,
    @JsonProperty("Grav") val grav: Planet.Level,
    @JsonProperty("Pres") val pres: Planet.Level,
    @JsonProperty("Temp") val temp: Planet.Level,
    @JsonProperty("tier") val tier: Int

)