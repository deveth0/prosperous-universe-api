/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.controller

import de.dev.eth0.prun.impl.model.Building
import de.dev.eth0.prun.impl.service.base.BuildingsService
import de.dev.eth0.prun.impl.service.base.model.Base
import de.dev.eth0.prun.impl.service.base.model.BaseCalculation
import de.dev.eth0.prun.service.BaseService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/base"], produces = [MediaType.APPLICATION_JSON_VALUE])
class BaseController @Autowired constructor(
    private val baseService: BaseService,
    private val buildingsService: BuildingsService
) {

  @ApiOperation("Retrieve information which buildings are available for a base")
  @GetMapping("/buildings")
  fun getBuildings(): Map<String, Building> {
    return buildingsService.buildings
  }

  @ApiOperation("Retrieve information on the building")
  @GetMapping("/buildings/{buildingId}")
  fun getBuildingDetails(@PathVariable("buildingId") buildingId: String): Building? {
    return buildingsService.getBuilding(buildingId)
  }

  @ApiOperation("Calculate the Base Facts")
  @PostMapping
  fun getBaseCalculation(@RequestBody base: Base): BaseCalculation {
    return baseService.calculate(base)
  }

}