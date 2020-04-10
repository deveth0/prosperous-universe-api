package de.dev.eth0.prun.impl.service.base.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class RestoredBase @JsonCreator constructor(
  @JsonProperty("base") val base: Base,
  @JsonProperty("calculation") val baseCalculation: BaseCalculation
)