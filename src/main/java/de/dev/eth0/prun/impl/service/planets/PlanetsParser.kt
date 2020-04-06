package de.dev.eth0.prun.impl.service.planets

import de.dev.eth0.prun.impl.model.Planet
import de.dev.eth0.prun.impl.model.PlanetaryResource
import de.dev.eth0.prun.impl.util.CsvUtil

class PlanetsParser constructor(private val planetsFile: String, private val planetaryResourcesFile: String) {

  fun getPlanets(): Map<String, Planet> {
    val planetsList = CsvUtil.loadObjectList(Planet::class.java, planetsFile)
    return planetsList.map { it.id to it }.toMap()
  }

  fun getPlanetaryResources(): Map<String, List<PlanetaryResource>> {
    val planetaryResources = CsvUtil.loadObjectList(PlanetaryResource::class.java, planetaryResourcesFile)
    return planetaryResources.groupBy { it.resourceId }
  }
}