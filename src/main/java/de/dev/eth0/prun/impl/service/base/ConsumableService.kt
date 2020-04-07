/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.base

import de.dev.eth0.prun.impl.service.base.model.PopulationLevel
import org.springframework.stereotype.Service

@Service
class ConsumableService {

  fun getConsumablesForPopulationLevel(populationLevel: PopulationLevel): String {
    return "foo"
  }
}