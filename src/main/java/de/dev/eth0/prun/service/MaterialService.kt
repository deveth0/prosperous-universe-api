/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.service

import de.dev.eth0.prun.impl.model.Material

/**
 * Service dealing with Materials
 */
interface MaterialService {
  /**
   * get all materials
   */
  fun getMaterials(): Map<String, Material>

}