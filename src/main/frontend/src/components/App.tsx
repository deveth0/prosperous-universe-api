/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

import {Container, CssBaseline} from "@material-ui/core";
import {Alert} from "@material-ui/lab";
import React from "react";
import {HashRouter} from "react-router-dom";

import {AppConfig} from "../js/model/AppConfig";
import {AppConfigReader} from "../js/appConfigReader";
import {Routes} from "./Routes";

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
      <Container>
        <HashRouter>
          <Routes/>
        </HashRouter>
      </Container>
    </AppContext.Provider>;
  }
  return <Alert severity={"info"}>Loading</Alert>;

}
