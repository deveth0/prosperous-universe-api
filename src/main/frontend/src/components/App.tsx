/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

import CssBaseline from "@material-ui/core/CssBaseline";
import React from "react";

import {AppConfig} from "../js/model/AppConfig";
import {AppConfigReader} from "../js/appConfigReader";
import {LoadingInfo} from "./loadingInfo/LoadingInfo";
import {PlanetList} from "./planetList/PlanetTable";

export const AppContext = React.createContext<AppConfig>({} as AppConfig);

export interface AppProps {
  appConfigUrl: string;
}

export function App(props: AppProps): JSX.Element {
  const [appConfig, setAppConfig] = React.useState<AppConfig>();
  React.useEffect(() => {
    const fetchData = async (): Promise<void> => {
      const appConfigReader = new AppConfigReader(props.appConfigUrl);
      const result = await appConfigReader.getAppConfig();
      setAppConfig(result);
    };
    fetchData();
  }, []);

  if (appConfig) {
    return <AppContext.Provider value={appConfig}>
      <CssBaseline/>
      <PlanetList/>
    </AppContext.Provider>;
  }
  return <LoadingInfo isLoading={true} isError={false}/>;

}
