/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

export interface Planet {
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
}

export interface PlanetaryResource {
  resourceId: string;
  form: string;
  concentration: number;
}