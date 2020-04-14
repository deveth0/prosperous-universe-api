/*
 * Copyright (c) 2019. dev-eth0.de
 * All rights reserved.
 */
import { Route, Switch } from "react-router-dom";
import * as React from "react";

import { PlanetsPage } from "./pages/PlanetsPage";
import { HomePage } from "./pages/HomePage";
import { BasePage } from "./pages/BasePage";

export const Routes = (): JSX.Element =>
  <Switch>
    <Route path="/" exact component={HomePage}/>
    <Route path="/planets" exact component={PlanetsPage}/>
    <Route path="/base" exact component={BasePage}/>
  </Switch>;