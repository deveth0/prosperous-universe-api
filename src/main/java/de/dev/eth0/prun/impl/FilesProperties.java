package de.dev.eth0.prun.impl;

import javax.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration of the files used to serve
 */
@ConfigurationProperties("app.files")
@Validated
public class FilesProperties {

  @NotBlank
  private String planets;
  @NotBlank
  private String resources;
  @NotBlank
  private String planetaryResources;

  public String getPlanets() {
    return planets;
  }

  public void setPlanets(String planets) {
    this.planets = planets;
  }

  public String getResources() {
    return resources;
  }

  public void setResources(String resources) {
    this.resources = resources;
  }

  public String getPlanetaryResources() {
    return planetaryResources;
  }

  public void setPlanetaryResources(String planetaryResources) {
    this.planetaryResources = planetaryResources;
  }
}
