/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

export class Planet {
  id: string;
  name: string;
  system: string;
  fertility: number;
  gravity: number;
  plots: number;
  pressure: number;
  temperature: number;
  type: string;
  gravityLevel: string;
  pressureLevel: string;
  temperatureLevel: string;
  tier: number;
  resources: Map<string, PlanetaryResource>;

  constructor(id: string, name: string, system: string, fertility: number, gravity: number, plots: number, pressure: number, temperature: number, type: string, gravityLevel: string, pressureLevel: string, temperatureLevel: string, tier: number, resources: Map<string, PlanetaryResource>) {
    this.id = id;
    this.name = name;
    this.system = system;
    this.fertility = fertility;
    this.gravity = gravity;
    this.plots = plots;
    this.pressure = pressure;
    this.temperature = temperature;
    this.type = type;
    this.gravityLevel = gravityLevel;
    this.pressureLevel = pressureLevel;
    this.temperatureLevel = temperatureLevel;
    this.tier = tier;
    this.resources = resources;
  }

  static fromJson(json: Record<string, any>): Planet {
    const planet = Object.assign(Object.create(Planet.prototype), json);
    if (json.resources) {
      planet.resources = new Map(Object
        .values<PlanetaryResource>(json.resources)
        .map(resource => [resource.resourceId, resource]));
    }
    return planet;
  }
}

export interface PlanetaryResource {
  resourceId: string;
  form: string;
  concentration: number;
}

