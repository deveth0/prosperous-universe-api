/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.planets.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import de.dev.eth0.prun.impl.model.Planet

@JsonIgnoreProperties(ignoreUnknown = true)
data class CSVSystem @JsonCreator constructor(
    @JsonProperty("id") val id: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("planets") val planets: Int,
    @JsonProperty("jmps prom") val jmpsProm: Int,
    @JsonProperty("jmps mon") val jmpsMon: Int,
    @JsonProperty("jmps kat") val jmpsKat: Int
)