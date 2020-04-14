/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.controller

import de.dev.eth0.prun.impl.model.Building
import de.dev.eth0.prun.impl.service.base.BuildingsService
import de.dev.eth0.prun.impl.service.base.model.Base
import de.dev.eth0.prun.impl.service.base.model.BaseCalculation
import de.dev.eth0.prun.impl.service.base.model.RestoredBase
import de.dev.eth0.prun.service.BaseService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/v1/base"], produces = [MediaType.APPLICATION_JSON_VALUE])
class BaseController @Autowired constructor(
    private val baseService: BaseService,
    private val buildingsService: BuildingsService
) {

  @ApiOperation("Retrieve information which buildings are available for a base")
  @GetMapping("/buildings")
  fun getBuildings(): Map<String, Building> {
    return buildingsService.getBuildings()
  }

  @ApiOperation("Retrieve information on the building")
  @GetMapping("/buildings/{buildingId}")
  fun getBuildingDetails(@PathVariable("buildingId") buildingId: String): Building? {
    return buildingsService.getBuilding(buildingId)
  }

  @ApiOperation("Open a base")
  @GetMapping
  fun getBaseCalculation(@RequestParam("id") deeplink: String): RestoredBase? {
    return baseService.restoreBase(deeplink)
  }

  @ApiOperation("Calculate the Base Facts")
  @PostMapping
  fun getBaseCalculation(@RequestBody base: Base): BaseCalculation {
    return baseService.calculate(base)
  }
}