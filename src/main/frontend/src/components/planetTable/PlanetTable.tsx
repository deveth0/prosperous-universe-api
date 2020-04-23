/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

import * as React from "react";
import MaterialTable from "material-table";
import {CircularProgress} from "@material-ui/core";

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
        <li key={planetaryResource.resourceId} className={"pru-m-planetaryresources-list-item"}>
          <Material name={planetaryResource.resourceId}/>
          <div className={"pru-m-planetaryresources-list-item-desc"}>
            <span>
              Conc.: {Math.round(planetaryResource.concentration * 10000) / 100}%
              ({Math.round(planetaryResource.concentration * (planetaryResource.form === "ATMOSPHERIC" ? 60 : 70) * 10) / 10}/day)
            </span>
            <span>Form: {planetaryResource.form}</span>
          </div>
        </li>
    )}
  </ul>;

  const renderPlanetaryRequirements = (rowData: any) => <MaterialList materials={rowData.planetaryRequirements}/>;

  const filterResources = (term: Array<string>, rowData: any) =>
    term.every(t => rowData.resources.find((resource: PlanetaryResource) => resource.resourceId === t) !== undefined);

  if (planets.length > 0) {
    const allPlanetaryResources = new Set<string>();
    planets.forEach(planet => planet.resources.forEach(res => allPlanetaryResources.add(res.resourceId)));
    const planetaryResourceLookup = Object.assign({}, ...Array.from(allPlanetaryResources.values()).sort()
      .map(e => ({[e]: e})));

    const columns = [
      {
        title: "Name",
        field: "name",
        filtering: false,
        cellStyle: {verticalAlign: "top", lineHeight: "36px"}
      },
      {
        title: "Fertility",
        field: "fertility",
        filtering: false,
        searchable: false,
        cellStyle: {verticalAlign: "top", lineHeight: "36px"}
      },
      {
        title: "Resources",
        field: "resources",
        searchable: false,
        render: renderResources,
        customFilterAndSearch: filterResources,
        cellStyle: {verticalAlign: "top"},
        lookup: planetaryResourceLookup
      },
      {
        title: "Tier",
        field: "tier",
        cellStyle: {verticalAlign: "top", lineHeight: "36px"},
        lookup: {1: "1", 2: "2", 3: "3", 4: "4"}
      },
      {
        title: "Jumps Promitor",
        field: "jmpsPromitor",
        cellStyle: {verticalAlign: "top"},
        filtering: false,
        searchable: false,
      },
      {
        title: "Jumps Montem",
        field: "jmpsMontem",
        cellStyle: {verticalAlign: "top"},
        filtering: false,
        searchable: false,
      },
      {
        title: "Jumps Katoa",
        field: "jmpsKatoa",
        cellStyle: {verticalAlign: "top"},
        filtering: false,
        searchable: false,
      },
      {
        title: "Planetary Requirements",
        field: "planetaryRequirements",
        filtering: false,
        searchable: false,
        render: renderPlanetaryRequirements,
        cellStyle: {verticalAlign: "top"}
      }
    ];
    const data = planets.map(planet => (
      {
        name: `${planet.id} ${planet.name !== planet.id ? `(${planet.name})` : ""}`,
        fertility: planet.fertility !== -1 ? `${Math.round(planet.fertility * 10000) / 100}%` : "",
        resources: Array.from(planet.resources.values()),
        tier: planet.tier,
        jmpsPromitor: planet.jmpsPromitor !== -1 ? planet.jmpsPromitor : "",
        jmpsMontem: planet.jmpsMontem !== -1 ? planet.jmpsMontem : "",
        jmpsKatoa: planet.jmpsKatoa !== -1 ? planet.jmpsKatoa : "",
        planetaryRequirements: planet.planetaryRequirements
      })
    );
    return (
      <MaterialTable
        title="Planets"
        options={{
          filtering: true,
          padding: "dense",
          pageSize: 20,
          pageSizeOptions: [20, 50, 200, 1000],
        }}
        localization={{
          toolbar: {
            searchPlaceholder: "Search Planet"
          }
        }}
        columns={columns}
        data={data}
      />
    );
  }

  return <>Loading.. <CircularProgress/></>;
}