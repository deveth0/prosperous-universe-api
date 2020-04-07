/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.recipe.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CSVRecipeInputOutput(
    @JsonProperty("Key") val key: String,
    @JsonProperty("Material") val material: String,
    @JsonProperty("Amount") val amount: Int
)