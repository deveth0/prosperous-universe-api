/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "Planet", description = "All details on a planet")
data class Planet @JsonCreator constructor(
    @ApiModelProperty(value = "Unique Id of the Planet", example = "WN-506a")
    @JsonProperty("id") val id: String,
    @ApiModelProperty(value = "Name of the Planet or it's ID", example = "Proxion")
    @JsonProperty("name") val name: String,
    @ApiModelProperty(value = "System of the Planet", example = "WN-506")
    @JsonProperty("system") val system: String,
    @ApiModelProperty(value = "Fertility of the Planet", example = "0.22")
    @JsonProperty("fertility") val fertility: Double,
    @ApiModelProperty(value = "Gravity of the Planet", example = "1.12")
    @JsonProperty("gravity") val gravity: Double,
    @ApiModelProperty(value = "Number of Plots on the Planet", example = "400")
    @JsonProperty("plots") val plots: Int,
    @ApiModelProperty(value = "Pressure of the Planet", example = "0.94")
    @JsonProperty("pressure") val pressure: Double,
    @ApiModelProperty(value = "Temperature of the Planet", example = "8")
    @JsonProperty("temperature") val temperature: Double,
    @ApiModelProperty(value = "Type of the Planet", example = "ROCKY")
    @JsonProperty("type") val type: Type,
    @ApiModelProperty(value = "Gravity Level of the Planet", example = "NORMAL")
    @JsonProperty("gravityLevel") val gravityLevel: Level,
    @ApiModelProperty(value = "Pressure Level of the Planet", example = "NORMAL")
    @JsonProperty("pressureLevel") val pressureLevel: Level,
    @ApiModelProperty(value = "Temperature Level of the Planet", example = "NORMAL")
    @JsonProperty("temperatureLevel") val temperatureLevel: Level,
    @ApiModelProperty(value = "Tier of the Planet", example = "1")
    @JsonProperty("tier") val tier: Int,
    @ApiModelProperty("Jumps to Promitor", example = "1")
    @JsonProperty("jmpsPromitor") val jmpsPromitor: Int,
    @ApiModelProperty("Jumps to Montem", example = "1")
    @JsonProperty("jmpsMontem") val jmpsMontem: Int,
    @ApiModelProperty("Jumps to Katoa", example = "1")
    @JsonProperty("jmpsKatoa") val jmpsKatoa: Int,
    @ApiModelProperty(value = "All resources available on the Planet")
    @JsonProperty("resources") val resources: Map<String, PlanetaryResource>) {

  @ApiModelProperty(value = "Additional materials required to build on the Planet")
  @JsonProperty("planetaryRequirements")
  val planetaryRequirements: List<String> = Planet.getPlanetaryRequirements(type, pressureLevel, gravityLevel, temperatureLevel)

  companion object {
    fun getPlanetaryRequirements(type: Type, pressureLevel: Level, gravityLevel: Level, temperatureLevel: Level): List<String> {
      val requirements = mutableListOf<String>()
      requirements.add(if (type == Type.ROCKY) "MCG" else "AEF")
      if (pressureLevel == Level.LOW) {
        requirements.add("SEA")
      }
      if (pressureLevel == Level.HIGH) {
        requirements.add("HSE")
      }
      if (gravityLevel == Level.LOW) {
        requirements.add("MGC")
      }
      if (gravityLevel == Level.HIGH) {
        requirements.add("BL")
      }
      if (temperatureLevel == Level.LOW) {
        requirements.add("INF")
      }
      if (temperatureLevel == Level.HIGH) {
        requirements.add("TSH")
      }
      return requirements;
    }
  }

  enum class Level { HIGH, LOW, NORMAL }
  enum class Type { ROCKY, GASEOUS }
}

