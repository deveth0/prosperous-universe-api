/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.util

import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import java.io.File

/**
 * Util Class for CSV handling
 */
class CsvUtil {

  companion object {
    private val logger = LoggerFactory.getLogger(CsvUtil::class.java.simpleName)

    /**
     * Load the given file
     */
    fun <T> loadObjectList(type: Class<T>, fileName: String): List<T> {
      return try {
        val bootstrapSchema = CsvSchema.emptySchema().withHeader()
        val mapper = CsvMapper()
        val file: File = ClassPathResource(fileName).file
        val readValues: MappingIterator<T> = mapper.reader(type).with(bootstrapSchema).readValues(file)
        readValues.readAll()
      } catch (e: Exception) {
        logger.error("Could not parse $fileName", e)
        listOf()
      }
    }
  }
}