/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class AppConfigController {

  @GetMapping("/appConfig.json")
  fun getAppConfig(): ResponseEntity<Map<String, String>> {
    val appConfig = mapOf(
        "planets" to PlanetController.PLANETS_PATH,
        "buildings" to "/api/v1/base/buildings"
    )
    return ResponseEntity.ok(appConfig)
  }
}