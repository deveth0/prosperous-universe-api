/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.recipe.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CSVRecipe(
    @JsonProperty("Key") val key: String,
    @JsonProperty("Building") val building: String,
    @JsonProperty("Duration") val duration: Int
)