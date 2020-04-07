/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.base.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty


/**
 * Definition of the consumption
 */
data class PopulationConsumption @JsonCreator constructor(
    @JsonProperty("ticker") val ticker: String,
    @JsonProperty("amount") val amount: Float,
    @JsonProperty("essential") val essential: Boolean
)