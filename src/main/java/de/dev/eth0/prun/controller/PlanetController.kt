package de.dev.eth0.prun.controller

import de.dev.eth0.prun.impl.model.Planet
import de.dev.eth0.prun.service.PlanetsService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/planets"], produces = [MediaType.APPLICATION_JSON_VALUE])
class PlanetController @Autowired constructor(val planetsService: PlanetsService) {


  @ApiOperation("Retrieve information on the given planet")
  @GetMapping(path = ["/{planetId}"])
  fun planet(@PathVariable("planetId") planetId: String): Planet? {
    return planetsService.getPlanet(planetId)
  }

  @ApiOperation("Search for all planets with the requested resources")
  @GetMapping(path = ["/search"])
  fun search(@RequestParam("resource") resources: List<String>): List<Planet> {
    return planetsService.searchPlanets(resources)
  }
}