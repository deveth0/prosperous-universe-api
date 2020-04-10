package de.dev.eth0.prun.service

import de.dev.eth0.prun.impl.service.base.model.Base

interface DeeplinkService {
  fun resolveDeeplink(deeplink: String): Base

  fun createDeeplink(base: Base): String
}