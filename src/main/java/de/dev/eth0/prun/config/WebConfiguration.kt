package de.dev.eth0.prun.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
open class WebConfiguration : WebMvcConfigurer {
  override fun addViewControllers(registry: ViewControllerRegistry) {
    registry.addRedirectViewController("/", "/swagger-ui.html")
  }
}