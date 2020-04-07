package de.dev.eth0.prun.impl.service.planets

import de.dev.eth0.prun.impl.FilesProperties
import de.dev.eth0.prun.impl.model.Planet
import de.dev.eth0.prun.service.PlanetsService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
@EnableConfigurationProperties(FilesProperties::class)
open class PlanetsServiceImpl @Autowired constructor(filesProperties: FilesProperties) : PlanetsService {

  private val planets: Map<String, Planet>
  private val planetaryResources: Map<String, List<Planet>>
  private val logger = LoggerFactory.getLogger(PlanetsServiceImpl::class.java.simpleName)

  init {
    val planetsParser = CSVPlanetsParser(filesProperties.planets, filesProperties.planetaryResources)
    planets = planetsParser.planetsById
    planetaryResources = planetsParser.planetsByResource
  }

  @Cacheable("planets")
  override fun getPlanet(planetId: String): Planet? {
    logger.debug("getPlanet($planetId)")
    return planets[planetId.toLowerCase()]
  }

  @Cacheable("searchPlanets")
  override fun searchPlanets(resources: List<String>): Set<Planet> {
    logger.debug("searchPlanets($resources)")
    var results = mutableSetOf<Planet>()
    for (resource in resources) {
      if (results.isEmpty()) {
        results.addAll(planetaryResources[resource] ?: listOf())
      } else {
        results = results.intersect(planetaryResources[resource] ?: listOf()).toMutableSet()
      }
    }
    return results
  }
}