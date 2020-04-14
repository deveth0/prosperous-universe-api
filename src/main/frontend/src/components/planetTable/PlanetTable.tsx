/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

import * as React from "react";
import {Alert} from "@material-ui/lab";
import MaterialTable from "material-table";

import {AppContext} from "../App";
import {Planet, PlanetaryResource} from "../../js/model/Planet";
import {ApiLoader} from "../../js/apiLoader";
import {Material} from "../common/Material";
import {MaterialList} from "../common/MaterialList";
import "./PlanetTable.scss";

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

  const renderResources = (rowData: any) => <ul className={"pru-m-planetaryresources-list"}>
    {rowData.resources.map(
      (planetaryResource: PlanetaryResource) =>
        <li key={planetaryResource.resourceId}>
          <Material name={planetaryResource.resourceId}/>
          <span>({Math.round(planetaryResource.concentration * 10000) / 100}%, {planetaryResource.form})</span>
        </li>
    )}
  </ul>;

  const renderPlanetaryRequirements = (rowData: any) => <MaterialList materials={rowData.planetaryRequirements}/>;

  const filterResources = (term: string, rowData: any) =>
    rowData.resources.find((resource: PlanetaryResource) => resource.resourceId.toLowerCase() === term.toLowerCase()) !== undefined;


  if (planets.length > 0) {
    const columns = [
      {title: "Name", field: "name", filtering: false},
      {title: "Fertility", field: "fertility", filtering: false},
      {title: "Resources", field: "resources", render: renderResources, customFilterAndSearch: filterResources},
      {title: "Tier", field: "tier"},
      {title: "Planetary Requirements", field: "planetaryRequirements", render: renderPlanetaryRequirements},
    ];
    const data = planets.map(planet => (
      {
        name: `${planet.id} ${planet.name !== planet.id ? `(${planet.name})` : ""}`,
        fertility: planet.fertility,
        resources: Array.from(planet.resources.values()),
        tier: planet.tier,
        planetaryRequirements: planet.planetaryRequirements
      })
    );
    return (
      <MaterialTable
        title="Planets"
        options={{
          filtering: true,
          pageSize: 20,
          pageSizeOptions: [20, 50, 200, 1000]
        }}
        columns={columns}
        data={data}/>
    );
  }

  return <Alert severity="info">Loading</Alert>;
}