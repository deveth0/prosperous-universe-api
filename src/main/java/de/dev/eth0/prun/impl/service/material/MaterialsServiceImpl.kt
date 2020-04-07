/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.material

import de.dev.eth0.prun.impl.FilesProperties
import de.dev.eth0.prun.impl.model.Material
import de.dev.eth0.prun.service.MaterialService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
open class MaterialsServiceImpl @Autowired constructor(filesProperties: FilesProperties) : MaterialService {

  private val materials: Map<String, Material>

  init {
    val parser = MaterialsParser(filesProperties.resources)
    materials = parser.materialsById
  }

  @Cacheable("materials")
  override fun getMaterials(): Map<String, Material> {
    return materials
  }
}