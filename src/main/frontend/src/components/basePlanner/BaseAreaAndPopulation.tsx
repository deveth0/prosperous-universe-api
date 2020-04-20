/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */
import React from "react";
import {Grid, LinearProgress, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@material-ui/core";

import {Planet} from "../../js/model/Planet";
import {Building} from "../../js/model/Building";

interface BaseAreaAndPopulationProps {
  planet: Planet | undefined;
  buildings: Building[];
  habitations: Building[];
}

export function BaseAreaAndPopulation(props: BaseAreaAndPopulationProps) {
  const area = [...props.buildings, ...props.habitations].reduce((p, b) => p + b.area, 0);
  const normalizedArea = Math.min(area * 100 / 500, 100);
  return <Paper>
    <Grid container>
      <Grid item xs>
        <PopulationTable buildings={props.buildings} habitations={props.habitations}/>
      </Grid>
    </Grid>
    <Grid container>
      <Grid item xs>
        Area ({area} / 500):
        <LinearProgress
          variant="determinate"
          color={normalizedArea >= 100 ? "secondary" : "primary"}
          value={normalizedArea}/>
      </Grid>
    </Grid>
  </Paper>;
}

interface PopulationTableProps {
  buildings: Building[];
  habitations: Building[];
}

function PopulationTable(props: PopulationTableProps) {

  const population = {
    Pioneers: {
      required: props.buildings
        .reduce((p, c) => p + (c.pioneers ? c.pioneers : 0), 0),
      capacity: props.habitations
        .reduce((p, c) => p + (c.pioneers ? Math.abs(c.pioneers) : 0), 0)
    },
    Settlers: {
      required: props.buildings
        .reduce((p, c) => p + (c.settlers ? c.settlers : 0), 0),
      capacity: props.habitations
        .reduce((p, c) => p + (c.settlers ? Math.abs(c.settlers) : 0), 0)
    },
    Technicians: {
      required: props.buildings
        .reduce((p, c) => p + (c.technicians ? c.technicians : 0), 0),
      capacity: props.habitations
        .reduce((p, c) => p + (c.technicians ? Math.abs(c.technicians) : 0), 0)
    },
    Engineers: {
      required: props.buildings
        .reduce((p, c) => p + (c.engineers ? c.engineers : 0), 0),
      capacity: props.habitations
        .reduce((p, c) => p + (c.engineers ? Math.abs(c.engineers) : 0), 0)
    },
    Scientists: {
      required: props.buildings
        .reduce((p, c) => p + (c.scientists ? c.scientists : 0), 0),
      capacity: props.habitations
        .reduce((p, c) => p + (c.scientists ? Math.abs(c.scientists) : 0), 0)
    },
  };
  return <TableContainer>
    <Table size="small">
      <TableHead>
        <TableRow>
          <TableCell>Level</TableCell>
          <TableCell align="right">Required</TableCell>
          <TableCell align="right">Capacity</TableCell>
        </TableRow>
      </TableHead>
      <TableBody>

        {Object.entries(population).map(k =>
          <TableRow key={k[0]}>
            <TableCell>{k[0]}</TableCell>
            <TableCell align="right">{k[1].required}</TableCell>
            <TableCell align="right">{k[1].capacity}</TableCell>
          </TableRow>
        )}
      </TableBody>
    </Table>
  </TableContainer>;
}