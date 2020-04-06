package de.dev.eth0.prun.impl.util

import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import org.springframework.core.io.ClassPathResource
import java.io.File

/**
 * Util Class for CSV handling
 */
class CsvUtil {

  companion object {

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
        listOf()
      }
    }
  }
}