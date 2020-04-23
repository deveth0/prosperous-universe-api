/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.planets

import de.dev.eth0.prun.impl.model.Planet
import de.dev.eth0.prun.impl.model.PlanetaryResource
import de.dev.eth0.prun.impl.service.planets.model.CSVPlanet
import de.dev.eth0.prun.impl.service.planets.model.CSVPlanetaryResource
import de.dev.eth0.prun.impl.service.planets.model.CSVSystem
import de.dev.eth0.prun.impl.util.CsvUtil
import org.slf4j.LoggerFactory

/**
 * Parser for the planets
 */
class CSVPlanetsParser constructor(planetsFile: String, planetaryResourcesFile: String, systemsFile: String) {
  val planetsById: Map<String, Planet>
  val planetsByResource: Map<String, List<Planet>>
  private val logger = LoggerFactory.getLogger(CSVPlanetsParser::class.java.simpleName)

  init {
    val allPlanets = CsvUtil.loadObjectList(CSVPlanet::class.java, planetsFile)
    val allResources = CsvUtil.loadObjectList(CSVPlanetaryResource::class.java, planetaryResourcesFile)
    val allSystems = CsvUtil.loadObjectList(CSVSystem::class.java, systemsFile).map { it.id to it }.toMap()
    val resourcesByPlanet = allResources.groupBy { it.planetId }
    val resourcesByResourceId = allResources.groupBy { it.resourceId }

    //TODO: ensure app crashes if no planets or resources are found
    planetsById = allPlanets.map { it.id.toLowerCase() to buildPlanet(it, resourcesByPlanet.getOrDefault(it.id, listOf()), allSystems[it.system]) }.toMap()
    planetsByResource = resourcesByResourceId.mapValues { it.value.mapNotNull { res -> planetsById[res.planetId.toLowerCase()] } }
  }

  private fun buildPlanetaryResource(csvPlanetaryResource: CSVPlanetaryResource): PlanetaryResource {
    return PlanetaryResource(csvPlanetaryResource.resourceId, PlanetaryResource.Form.valueOf(csvPlanetaryResource.form.toUpperCase()), csvPlanetaryResource.conc)
  }

  private fun buildPlanet(csvPlanet: CSVPlanet, planetaryResources: List<CSVPlanetaryResource>, system: CSVSystem?): Planet {
    logger.debug("parsing $csvPlanet")
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
        Planet.Type.valueOf(csvPlanet.type.toUpperCase()),
        csvPlanet.grav,
        csvPlanet.pres,
        csvPlanet.temp,
        csvPlanet.tier,
        system?.jmpsProm ?: -1,
        system?.jmpsMon ?: -1,
        system?.jmpsKat ?: -1,
        resources
    )
  }
}