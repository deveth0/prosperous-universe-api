/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.planets

import de.dev.eth0.prun.impl.model.Planet
import de.dev.eth0.prun.impl.model.PlanetaryResource
import de.dev.eth0.prun.impl.service.planets.model.CSVPlanet
import de.dev.eth0.prun.impl.service.planets.model.CSVPlanetaryResource
import de.dev.eth0.prun.impl.util.CsvUtil

/**
 * Parser for the planets
 */
class CSVPlanetsParser constructor(planetsFile: String, planetaryResourcesFile: String) {
  val planetsById: Map<String, Planet>
  val planetsByResource: Map<String, List<Planet>>


  init {
    val allPlanets = CsvUtil.loadObjectList(CSVPlanet::class.java, planetsFile)
    val allResources = CsvUtil.loadObjectList(CSVPlanetaryResource::class.java, planetaryResourcesFile)
    val resourcesByPlanet = allResources.groupBy { it.planetId }
    val resourcesByResourceId = allResources.groupBy { it.resourceId }

    //TODO: ensure app crashes if no planets or resources are found
    planetsById = allPlanets.map { it.id.toLowerCase() to buildPlanet(it, resourcesByPlanet.getOrDefault(it.id, listOf())) }.toMap()
    planetsByResource = resourcesByResourceId.mapValues { it.value.mapNotNull { res -> planetsById[res.planetId.toLowerCase()] } }
  }

  private fun buildPlanetaryResource(csvPlanetaryResource: CSVPlanetaryResource): PlanetaryResource {
    return PlanetaryResource(csvPlanetaryResource.resourceId, csvPlanetaryResource.form, csvPlanetaryResource.conc)
  }

  private fun buildPlanet(csvPlanet: CSVPlanet, planetaryResources: List<CSVPlanetaryResource>): Planet {
    val resources = planetaryResources.map { it.resourceId to buildPlanetaryResource(it) }.toMap()
    return Planet(
        csvPlanet.id,
        csvPlanet.name,
        csvPlanet.system,
        csvPlanet.fertility,
        csvPlanet.gravity,
        csvPlanet.plots,
        csvPlanet.pressure,
        csvPlanet.temperature,
        csvPlanet.type,
        if (csvPlanet.highGravity) Planet.Level.HIGH else if (csvPlanet.lowGravity) Planet.Level.LOW else Planet.Level.NORMAL,
        if (csvPlanet.highPressure) Planet.Level.HIGH else if (csvPlanet.lowPressure) Planet.Level.LOW else Planet.Level.NORMAL,
        if (csvPlanet.highTemperature) Planet.Level.HIGH else if (csvPlanet.lowTemperature) Planet.Level.LOW else Planet.Level.NORMAL,
        csvPlanet.tier,
        resources
    )
  }
}