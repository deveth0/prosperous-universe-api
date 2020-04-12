/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.controller

import de.dev.eth0.prun.impl.model.Planet
import de.dev.eth0.prun.service.PlanetsService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/planets"], produces = [MediaType.APPLICATION_JSON_VALUE])
class PlanetController @Autowired constructor(val planetsService: PlanetsService) {

  @ApiOperation("Get all planets")
  @GetMapping
  fun planets(): Map<String, Planet> {
    return planetsService.getPlanets()
  }

  @ApiOperation("Retrieve information on the given planet (either by Id or Name)")
  @GetMapping(path = ["/{planetId}"])
  fun planet(@PathVariable("planetId") planetId: String): Planet? {
    return planetsService.getPlanet(planetId)
  }

  @ApiOperation("Search for all planets with the requested parameters")
  @GetMapping(path = ["/search"])
  fun search(
    @RequestParam("resource", required = false) resources: List<String>?,
    @RequestParam("fertile", required = false) fertile: Boolean?
  ): Map<String, Planet> {
    return planetsService.searchPlanets(resources ?: listOf(), fertile ?: false).map { it.id to it }.toMap()
  }
}