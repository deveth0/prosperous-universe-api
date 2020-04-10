package de.dev.eth0.prun.impl.service.deeplink

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import de.dev.eth0.prun.impl.service.base.model.Base
import de.dev.eth0.prun.service.DeeplinkService
import org.apache.commons.io.IOUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.stereotype.Service
import org.springframework.web.util.UriUtils
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.Base64
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

@Service
class DeeplinkServiceImpl
@Autowired constructor(jackson2ObjectMapperBuilder: Jackson2ObjectMapperBuilder) : DeeplinkService {
  private val logger = LoggerFactory.getLogger(DeeplinkServiceImpl::class.java.simpleName)
  private val objectMapper: ObjectMapper = jackson2ObjectMapperBuilder.serializationInclusion(JsonInclude.Include.NON_NULL).build()

  override fun resolveDeeplink(deeplink: String): Base {
    val decrypted = decrypt(deeplink)
    return objectMapper.readValue(decrypted, Base::class.java)
  }

  override fun createDeeplink(base: Base): String {
    val baseJson = objectMapper.writeValueAsString(base)
    return encrypt(baseJson)
  }

  private fun encrypt(plainText: String): String {
    return try {
      val zippedData = zip(plainText)
      //val encryptedData: ByteArray = encryptionUtil.encrypt(zippedData)
      UriUtils.encode(Base64.getEncoder().encodeToString(zippedData), Charsets.UTF_8)
    } catch (ex: IOException) {
      logger.error("Could not zip string", ex)
      throw ex
    }
  }

  private fun decrypt(encrypted: String): String {
    return try {
      val message: ByteArray = Base64.getDecoder().decode(UriUtils.decode(encrypted, Charsets.UTF_8))
      //val decryptedData: ByteArray = encryptionUtil.decrypt(message)
      unzip(message)
    } catch (ex: IOException) {
      logger.error("Could not unzip string", ex)
      throw ex
    }
  }

  @Throws(IOException::class)
  private fun zip(toZip: String): ByteArray {
    val obj = ByteArrayOutputStream()
    GZIPOutputStream(obj).use { gzip ->
      gzip.write(toZip.toByteArray(Charsets.UTF_8))
      gzip.flush()
      gzip.close()
      return obj.toByteArray()
    }
  }

  @Throws(IOException::class)
  private fun unzip(toUnzip: ByteArray): String {
    GZIPInputStream(ByteArrayInputStream(toUnzip)).use { zi -> return IOUtils.toString(zi, Charsets.UTF_8) }
  }
}