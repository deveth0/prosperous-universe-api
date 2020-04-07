package de.dev.eth0.prun.service

import de.dev.eth0.prun.impl.model.Planet

/**
 * Service for everything Planet related
 */
interface PlanetsService {

  /**
   * Return the Planet with the given Id
   */
  fun getPlanet(planetId: String): Planet?

  /**
   * Return a list of Planets that contain all of the requested resources
   */
  fun searchPlanets(resources: List<String>): Set<Planet>
}