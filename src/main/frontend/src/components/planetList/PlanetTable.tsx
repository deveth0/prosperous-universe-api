/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

import * as React from "react";
import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TableSortLabel, Typography} from "@material-ui/core";

import {AppContext} from "../App";
import {Planet} from "../../js/model/Planet";
import {ApiLoader} from "../../js/apiLoader";
import {LoadingInfo} from "../loadingInfo/LoadingInfo";

function descendingComparator<T>(a: T, b: T, orderBy: keyof T) {
  if (b[orderBy] < a[orderBy]) {
    return -1;
  }
  if (b[orderBy] > a[orderBy]) {
    return 1;
  }
  return 0;
}

type Order = "asc" | "desc";

function getComparator<Key extends keyof any>(
  order: Order,
  orderBy: Key,
): (a: { [key in Key]: number | string | any }, b: { [key in Key]: number | string | any }) => number {
  return order === "desc"
    ? (a, b) => descendingComparator(a, b, orderBy)
    : (a, b) => -descendingComparator(a, b, orderBy);
}

function stableSort<T>(array: T[], comparator: (a: T, b: T) => number) {
  const stabilizedThis = array.map((el, index) => [el, index] as [T, number]);
  stabilizedThis.sort((a, b) => {
    const order = comparator(a[0], b[0]);
    if (order !== 0) return order;
    return a[1] - b[1];
  });
  return stabilizedThis.map(el => el[0]);
}

/**
 * Display a list with all planets
 * @constructor
 */
export function PlanetList(): JSX.Element {
  const [planets, setAvailablePlanets] = React.useState<Planet[]>([]);

  const [order, setOrder] = React.useState<Order>("asc");
  const [orderBy, setOrderBy] = React.useState<keyof Planet>("id");

  const appConfig = React.useContext(AppContext);

  // load all planets into context
  React.useEffect(() => {
    if (planets.length === 0) {
      const apiLoader = new ApiLoader(appConfig);
      apiLoader.getPlanets(setAvailablePlanets);
    }
  }, []);

  if (planets) {

    const handleRequestSort = (event: React.MouseEvent<unknown>, property: keyof Planet) => {
      const isAsc = orderBy === property && order === "asc";
      setOrder(isAsc ? "desc" : "asc");
      setOrderBy(property);
    };
    const createSortHandler = (property: keyof Planet) => (event: React.MouseEvent<unknown>) => {
      handleRequestSort(event, property);
    };

    return (
      <React.Fragment>
        <Typography variant="h6" component="div">
          Planets
        </Typography>
        <TableContainer component={Paper}>
          <Table aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell>Name</TableCell>
                <TableCell align="right">
                  <TableSortLabel
                    active={orderBy === "fertility"}
                    direction={orderBy === "fertility" ? order : "asc"}
                    onClick={createSortHandler("fertility")}
                  >
                    Fertility
                  </TableSortLabel>
                </TableCell>
                <TableCell align="right">Resources</TableCell>
                <TableCell align="right">Tier</TableCell>
                <TableCell align="right">Gravity Level</TableCell>
                <TableCell align="right">Pressure Level</TableCell>
                <TableCell align="right">Temperature Level</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {stableSort(planets, getComparator(order, orderBy))
                .map(planet => (
                  <TableRow key={planet.name}>
                    <TableCell component="th" scope="row">
                      {`${planet.id} ${planet.name !== planet.id ? `(${planet.name})` : ""}`}
                    </TableCell>
                    <TableCell align="right">{planet.fertility}</TableCell>
                    <TableCell align="right">{Object.keys(planet.resources).join(", ")}</TableCell>
                    <TableCell align="right">{planet.tier}</TableCell>
                    <TableCell align="right">{planet.gravityLevel}</TableCell>
                    <TableCell align="right">{planet.pressureLevel}</TableCell>
                    <TableCell align="right">{planet.temperatureLevel}</TableCell>
                  </TableRow>
                ))}
            </TableBody>
          </Table>
        </TableContainer>
      </React.Fragment>
    );
  }

  return <LoadingInfo isError={false} isLoading={true}/>;
}