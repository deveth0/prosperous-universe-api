/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.service

import de.dev.eth0.prun.impl.model.Planet

/**
 * Service for everything Planet related
 */
interface PlanetsService {

  /**
   * Get all planets
   */
  fun getPlanets(): Map<String, Planet>

  /**
   * Return the Planet with the given Id or name
   */
  fun getPlanet(planetId: String): Planet?

  /**
   * Return a list of Planets that matches the given parameters
   *
   */
  fun searchPlanets(resources: List<String>, fertileOnly: Boolean): Set<Planet>
}