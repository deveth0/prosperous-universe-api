package de.dev.eth0.prun.impl.service.base.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import de.dev.eth0.prun.impl.util.MathUtil
import io.swagger.annotations.ApiModelProperty

class BaseMaterials @JsonCreator constructor(
  @ApiModelProperty("Materials consumed by the base")
  @JsonProperty("input") private val input: MutableMap<String, Double> = mutableMapOf(),
  @ApiModelProperty("Materials produced by the base")
  @JsonProperty("output") private val output: MutableMap<String, Double> = mutableMapOf()
) {

  fun getInput(): Map<String, Double> {
    return input.mapValues { MathUtil.round(it.value, 2) }
  }

  fun getOutput(): Map<String, Double> {
    return output.mapValues { MathUtil.round(it.value, 2) }
  }

  @ApiModelProperty("Resulting usage/production")
  @JsonProperty("sum")
  fun getSum(): Map<String, Double> {
    val inp = input.mapValues { it.value * -1 }.toMutableMap()
    for ((k, v) in output) {
      inp.merge(k, v) { v1, v2 -> v1 + v2 }
    }
    return inp.mapValues { MathUtil.round(it.value, 2) }
  }

  fun add(toAdd: BaseMaterials) {
    for ((k, v) in toAdd.input) {
      input.merge(k, v) { v1, v2 -> v1 + v2 }
    }
    for ((k, v) in toAdd.output) {
      output.merge(k, v) { v1, v2 -> v1 + v2 }
    }
  }

  companion object {
    fun multiply(baseMaterials: BaseMaterials, factor: Double): BaseMaterials {
      val input = baseMaterials.input.mapValues { (_, v) -> v * factor }.toMutableMap()
      val output = baseMaterials.output.mapValues { (_, v) -> v * factor }.toMutableMap()
      return BaseMaterials(input, output)
    }
  }
}