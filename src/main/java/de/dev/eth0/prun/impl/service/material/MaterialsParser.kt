/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.service.material

import de.dev.eth0.prun.impl.model.Material
import de.dev.eth0.prun.impl.util.CsvUtil
import org.slf4j.LoggerFactory

/**
 * Parser for the materials
 */
class MaterialsParser constructor(materialsFile: String) {
  val materialsById: Map<String, Material>
  private val logger = LoggerFactory.getLogger(MaterialsParser::class.java.simpleName)

  init {
    val allMaterials = CsvUtil.loadObjectList(Material::class.java, materialsFile)
    materialsById = allMaterials.map { it.ticker to it }.toMap()
  }

}