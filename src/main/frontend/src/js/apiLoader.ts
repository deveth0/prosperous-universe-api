/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

import {AppConfig} from "./model/AppConfig";
import {Planet} from "./model/Planet";
import {isApiError} from "./model/ApiError";
import {Building} from "./model/Building";

/**
 * Loader for any Game API related stuff
 */
export class ApiLoader {

  private appConfig: AppConfig;

  constructor(appConfig: AppConfig) {
    this.appConfig = appConfig;
  }

  /**
   * Load the list of all planets
   */
  getPlanets(callback: (planets: Planet[]) => void): void {
    fetch(this.appConfig.planets)
      .then(r => r.json())
      .then(obj => {
        if (isApiError(obj)) {
          throw new Error(obj.message);
        }
        else {
          callback(Object.keys(obj).map(k => Planet.fromJson(obj[k])));
        }
      })
      .catch(error => console.error(error));
  }

  /**
   * Load the list of all buildings
   */
  getBuildings(callback: (buildings: Building[]) => void): void {
    fetch(this.appConfig.buildings)
      .then(r => r.json())
      .then(obj => {
        if (isApiError(obj)) {
          throw new Error(obj.message);
        }
        else {
          callback(Object.keys(obj).map(k => (obj[k])));
        }
      })
      .catch(error => console.error(error));
  }


}