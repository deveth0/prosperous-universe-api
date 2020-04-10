/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.service

import de.dev.eth0.prun.impl.service.base.model.Base
import de.dev.eth0.prun.impl.service.base.model.BaseCalculation
import de.dev.eth0.prun.impl.service.base.model.RestoredBase

/**
 * Service for everything base related
 */
interface BaseService {
  /**
   * Calculate the facts for the given base
   */
  fun calculate(base: Base): BaseCalculation

  fun restoreBase(deeplink: String): RestoredBase?
}