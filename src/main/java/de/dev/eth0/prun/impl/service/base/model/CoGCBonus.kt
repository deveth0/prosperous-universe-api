/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.base.model

/**
 * The available CoGC Bonus values
 */
enum class CoGCBonus(val bonus: Double) {

  AGRICULTURE(0.25),
  CHEMISTRY(0.25),
  CONSTRUCTION(0.25),
  ELECTRONICS(0.25),
  FOOD_INDUSTRIES(0.25),
  FUEL_REFINING(0.25),
  MANUFACTURING(0.25),
  METALLURGY(0.25),
  RESOURCE_EXTRACTION(0.25),
  PIONEERS(0.1),
  SETTLERS(0.1),
  TECHNICIANS(0.1),
  ENGINEERS(0.1),
  SCIENTISTS(0.1)

}