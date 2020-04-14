/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.controller

import de.dev.eth0.prun.impl.model.Material
import de.dev.eth0.prun.service.MaterialService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(path = ["/api/v1/materials"], produces = [MediaType.APPLICATION_JSON_VALUE])
class MaterialController @Autowired constructor(val materialService: MaterialService) {

  @ApiOperation("Get all materials")
  @GetMapping
  fun materials(): Map<String, Material> {
    return materialService.getMaterials()
  }
}