package de.dev.eth0.prun.service

import de.dev.eth0.prun.impl.model.Planet

interface PlanetsService {

  fun getPlanet(planetId: String): Planet?

  fun searchPlanets(resources: List<String>): List<Planet>
}