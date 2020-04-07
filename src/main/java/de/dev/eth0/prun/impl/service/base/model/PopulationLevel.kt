/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.base.model

/**
 * The different levels of population / workers
 */
enum class PopulationLevel(val tier: Int) {
  PIONEERS(1), SETTLERS(2), TECHNICANS(3), ENGINEERS(4), SCIENTISTS(5)
}