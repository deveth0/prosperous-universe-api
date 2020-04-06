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
    @JsonProperty("low grav") val lowGravity: Boolean,
    @JsonProperty("high grav") val highGravity: Boolean,
    @JsonProperty("low pres") val lowPressure: Boolean,
    @JsonProperty("high pres") val highPressure: Boolean,
    @JsonProperty("low temp") val lowTemperature: Boolean,
    @JsonProperty("high temp") val highTemperature: Boolean,
    @JsonProperty("tier") val tier: Int

)