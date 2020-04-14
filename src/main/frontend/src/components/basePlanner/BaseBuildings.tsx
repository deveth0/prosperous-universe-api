/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */
import React from "react";
import {Grid, Paper} from "@material-ui/core";
import MaterialTable from "material-table";

import {Building} from "../../js/model/Building";

interface BaseBuildingsProps {
  availableBuildings: Building[];
  availableHabitations: Building[];
  currentBuildings: Building[];
  currentHabitations: Building[];
  setCurrentBuildings: (currentBuildings: Building[]) => void;
  setCurrentHabitations: (currentHabitations: Building[]) => void;
}

export function BaseBuildings(props: BaseBuildingsProps) {
  return <Paper>
    <Grid container>
      <Grid item xs>
        <h3>Production</h3>
        <BuildingTable
          available={props.availableBuildings}
          current={props.currentBuildings}
          setCurrent={props.setCurrentBuildings}/>
      </Grid>
      <Grid item xs>
        <h3>Habitations</h3>
        <BuildingTable
          available={props.availableHabitations}
          current={props.currentHabitations}
          setCurrent={props.setCurrentHabitations}/>
      </Grid>
    </Grid>
  </Paper>;
}

interface BuildingTableProps {
  available: Building[];
  current: Building[];
  setCurrent: (buildings: Building[]) => void;
}

function BuildingTable(_props: BuildingTableProps) {
  return <MaterialTable
    columns={[
      { title: "Adı", field: "name" },
      { title: "Soyadı", field: "surname" },
      { title: "Doğum Yılı", field: "birthYear", type: "numeric" },
      { title: "Doğum Yeri", field: "birthCity", lookup: { 34: "İstanbul", 63: "Şanlıurfa" } }
    ]}
    data={[{ name: "Mehmet", surname: "Baran", birthYear: 1987, birthCity: 63 }]}
    title="Demo Title"
  />;
}