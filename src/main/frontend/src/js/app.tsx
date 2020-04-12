/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

import * as ReactDOM from "react-dom";
import * as React from "react";

import {App} from "../components/App";

const rootElem = document.getElementById("root") as HTMLElement;

if (rootElem && rootElem.dataset.appConfigUrl) {

  const appConfigUrl = rootElem.dataset.appConfigUrl;
  // render app
  ReactDOM.render(
    <App appConfigUrl={appConfigUrl}/>,
    rootElem
  );

}