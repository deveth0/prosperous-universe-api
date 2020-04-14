/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */
import React from "react";
import {FormControl, InputLabel, MenuItem, Paper, Select, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@material-ui/core";
import {Alert} from "@material-ui/lab";

import {Planet} from "../../js/model/Planet";
import {AppContext} from "../App";
import {ApiLoader} from "../../js/apiLoader";

interface PlanetSelectionProps {
  selectPlanet: (planet: Planet) => void;
  selectedPlanet: Planet | undefined;
}

export function PlanetSelection(props: PlanetSelectionProps) {
  const [planets, setAvailablePlanets] = React.useState<Planet[]>([]);
  const {selectedPlanet} = props;
  const appConfig = React.useContext(AppContext);

  // load all planets into context
  React.useEffect(() => {
    if (planets.length === 0) {
      const apiLoader = new ApiLoader(appConfig);
      apiLoader.getPlanets(setAvailablePlanets);
    }
  }, []);

  const handlePlanetSelect = (event: React.ChangeEvent<{ value: unknown }>) => {
    if (planets.length > 0) {
      // TODO: replace Planet[] with a map to simplify this
      const newPlanet = planets.find(planet => planet.id === event.target.value);
      if (newPlanet) {
        props.selectPlanet(newPlanet);
      }
    }
  };

  if (planets.length > 0) {
    return <Paper>
      <FormControl fullWidth={true}>
        <InputLabel id="planet-select-label">Planet: </InputLabel>
        <Select
          labelId="planet-select-label"
          value={selectedPlanet ? selectedPlanet.id : ""}
          onChange={handlePlanetSelect}>
          {planets.map(planet =>
            <MenuItem key={planet.id} value={planet.id}>
              {`${planet.id} ${planet.name !== planet.id ? `(${planet.name})` : ""}`}
            </MenuItem>)}
        </Select>
      </FormControl>
      {selectedPlanet && <PlanetInfo planet={selectedPlanet}/>}
    </Paper>;
  }
  return <Alert severity={"info"}>Loading Planets</Alert>;
}

interface PlanetInfoProps {
  planet: Planet;
}

function PlanetInfo(props: PlanetInfoProps) {

  return <TableContainer>
    <Table size="small">
      <TableHead>
        <TableRow>
          <TableCell>Resource</TableCell>
          <TableCell align="right">Concentration</TableCell>
          <TableCell align="right">Form</TableCell>
        </TableRow>
      </TableHead>
      <TableBody>
        <TableRow>
          <TableCell>Fertility</TableCell>
          <TableCell align="right">{props.planet.fertility}</TableCell>
          <TableCell></TableCell>
        </TableRow>
        {Array.from(props.planet.resources.values()).map(resource =>
          <TableRow key={resource.resourceId}>
            <TableCell>{resource.resourceId}</TableCell>
            <TableCell align="right">{resource.concentration}</TableCell>
            <TableCell align="right">{resource.form}</TableCell>
          </TableRow>
        )}
      </TableBody>
    </Table>
  </TableContainer>;
}