package de.dev.eth0.prun.impl.service.planets

import de.dev.eth0.prun.impl.FilesProperties
import de.dev.eth0.prun.impl.model.Planet
import de.dev.eth0.prun.impl.model.PlanetaryResource
import de.dev.eth0.prun.service.PlanetsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service

@Service
@EnableConfigurationProperties(FilesProperties::class)
class PlanetsServiceImpl @Autowired constructor(filesProperties: FilesProperties) : PlanetsService {

  private val planets: Map<String, Planet>
  private val planetaryResources: Map<String, List<PlanetaryResource>>

  init {
    val planetsParser = PlanetsParser(filesProperties.planets, filesProperties.planetaryResources)
    planets = planetsParser.getPlanets()
    planetaryResources = planetsParser.getPlanetaryResources()
  }

  override fun getPlanet(planetId: String): Planet? {
    return planets[planetId]
  }

  override fun searchPlanets(resources: List<String>): List<Planet> {
    val results = mutableListOf<Planet>()
    for (resource in resources) {
      results.addAll(planetaryResources[resource]?.mapNotNull { getPlanet(it.planetId) } ?: listOf())
    }
    return results
  }
}