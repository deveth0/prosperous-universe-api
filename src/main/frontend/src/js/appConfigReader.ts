/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

import {AppConfig} from "./model/AppConfig";

/**
 * Reader for the AppConfig
 */
export class AppConfigReader {

  appConfigUrl: string;

  constructor(appConfigUrl: string) {
    this.appConfigUrl = appConfigUrl;
  }

  /**
   * Load and parse the AppConfig
   */
  async getAppConfig(): Promise<AppConfig> {
    const response = await fetch(this.appConfigUrl);
    const data = await response.json();
    return data;
  }

}