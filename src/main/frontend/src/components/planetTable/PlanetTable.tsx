/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

import * as React from "react";
import {Alert} from "@material-ui/lab";
import MaterialTable from "material-table";

import {AppContext} from "../App";
import {Planet} from "../../js/model/Planet";
import {ApiLoader} from "../../js/apiLoader";

/**
 * Display a list with all planets
 * @constructor
 */
export function PlanetTable(): JSX.Element {
  const [planets, setAvailablePlanets] = React.useState<Planet[]>([]);

  const appConfig = React.useContext(AppContext);

  // load all planets into context
  React.useEffect(() => {
    if (planets.length === 0) {
      const apiLoader = new ApiLoader(appConfig);
      apiLoader.getPlanets(setAvailablePlanets);
    }
  }, []);

  if (planets.length > 0) {
    const columns = [
      {title: "Name", field: "name", filtering: false},
      {title: "Fertility", field: "fertility"},
      {title: "Resources", field: "resources"},
      {title: "Tier", field: "tier"},
      {title: "Planetary Requirements", field: "planetaryRequirements"},
    ];
    const data = planets.map(planet => (
      {
        name: `${planet.id} ${planet.name !== planet.id ? `(${planet.name})` : ""}`,
        fertility: planet.fertility,
        resources: Array.from(planet.resources.keys()).join(", "),
        tier: planet.tier,
        planetaryRequirements: planet.planetaryRequirements.join(", ")
      })
    );
    return (
      <MaterialTable
        title="Planets"
        options={{
          filtering: true,
          pageSize: 20,
          pageSizeOptions: [10, 50, 200, 1000]
        }}
        columns={columns}
        data={data}/>
    );
  }

  return <Alert severity="info">Loading</Alert>;
}