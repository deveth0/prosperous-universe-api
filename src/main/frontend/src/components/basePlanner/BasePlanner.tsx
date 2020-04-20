import React from "react";
import {Grid, Paper} from "@material-ui/core";
import {makeStyles, createStyles, Theme} from "@material-ui/core/styles";
import {useLocation, useHistory} from "react-router-dom";
import qs from "qs";

import {Planet} from "../../js/model/Planet";
import {PlanetSelection} from "./PlanetSelection";
import {BaseAreaAndPopulation} from "./BaseAreaAndPopulation";
import {AppContext} from "../App";
import {ApiLoader} from "../../js/apiLoader";
import {Building} from "../../js/model/Building";
import {BaseBuildings} from "./BaseBuildings";

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    paper: {
      padding: theme.spacing(1),
      textAlign: "center",
      color: theme.palette.text.secondary,
    },
  }),
);

export function BasePlanner(): JSX.Element {
  const classes = useStyles();

  const history = useHistory();
  const location = useLocation();
  const queryParams = qs.parse(location.search, {ignoreQueryPrefix: true, arrayLimit: 40});
  // TODO: restore buildings


  // Available Buildings / Habitations
  const [availableBuildings, setAvailableBuildings] = React.useState<Building[]>([]);
  const [availableHabitations, setAvailableHabitations] = React.useState<Building[]>([]);
  // Current Buildings / Habitations
  const [currentBuildings, setCurrentBuildings] = React.useState<Building[]>([]);
  const [currentHabitations, setCurrentHabitations] = React.useState<Building[]>([]);

  const appConfig = React.useContext(AppContext);

  const setSelectedBuildings = (buildings: Building[]) => {
    setCurrentBuildings(buildings);
    queryParams.prod = buildings.map(b => b.id);
    location.search = qs.stringify(queryParams, {encodeValuesOnly: true});
    history.push(location);
  };
  const setSelectedHabitations = (buildings: Building[]) => {
    setCurrentHabitations(buildings);
    queryParams.habit = buildings.map(b => b.id);
    location.search = qs.stringify(queryParams, {encodeValuesOnly: true});
    history.push(location);
  };

  const setBuildings = (loadedBuildings: Building[]) => {
    const productionBuildings = loadedBuildings
      .filter(building =>
        building.pioneers > 0
        || building.settlers > 0
        || building.technicians > 0
        || building.engineers > 0
        || building.scientists > 0
      );
    setAvailableBuildings(productionBuildings);
    if (queryParams.prod) {
      const restoredBuildings = queryParams.prod
        .map((b: string) => productionBuildings.find(id => b === id.id))
        .filter((b: Building | undefined) => b !== undefined);
      setCurrentBuildings(restoredBuildings);
    }
    const habitationBuildings = loadedBuildings
      .filter(building =>
        building.pioneers < 0
        || building.settlers < 0
        || building.technicians < 0
        || building.engineers < 0
        || building.scientists < 0
      );
    setAvailableHabitations(habitationBuildings);
    if (queryParams.habit) {
      const restoredBuildings = queryParams.habit
        .map((b: string) => habitationBuildings.find(id => b === id.id))
        .filter((b: Building | undefined) => b !== undefined);
      setCurrentHabitations(restoredBuildings);
    }
  };

  React.useEffect(() => {
    if (availableBuildings.length === 0) {
      const apiLoader = new ApiLoader(appConfig);
      apiLoader.getBuildings(setBuildings);
    }
  }, []);

  const [planet, setSelectedPlanet] = React.useState<Planet | undefined>();

  return <div>
    <Grid container spacing={3}>
      <Grid className={classes.paper} item xs>
        <PlanetSelection selectPlanet={setSelectedPlanet} selectedPlanet={planet}/>
      </Grid>
      <Grid className={classes.paper} item xs>
        <BaseAreaAndPopulation planet={planet} buildings={currentBuildings} habitations={currentHabitations}/>
      </Grid>
      <Grid className={classes.paper} item xs>
        <Paper className={classes.paper}>CoGC and Experts</Paper>
      </Grid>
    </Grid>
    <Grid container spacing={3}>
      <Grid className={classes.paper} item xs>
        <BaseBuildings
          availableBuildings={availableBuildings}
          availableHabitations={availableHabitations}
          currentBuildings={currentBuildings}
          currentHabitations={currentHabitations}
          setCurrentBuildings={setSelectedBuildings}
          setCurrentHabitations={setSelectedHabitations}/>
      </Grid>
    </Grid>
  </div>;
}