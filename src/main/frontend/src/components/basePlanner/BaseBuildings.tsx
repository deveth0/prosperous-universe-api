/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */
import React from "react";
import {Dialog, DialogTitle, Grid, MenuItem, Select} from "@material-ui/core";
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
  return <Grid container spacing={3}>
    <Grid item xs>
      <BuildingTable
        title={"Production"}
        available={props.availableBuildings}
        current={props.currentBuildings}
        setCurrent={props.setCurrentBuildings}/>
    </Grid>
    <Grid item xs>
      <BuildingTable
        title={"Habitations"}
        available={props.availableHabitations}
        current={props.currentHabitations}
        setCurrent={props.setCurrentHabitations}/>
    </Grid>
  </Grid>;
}

interface BuildingTableProps {
  title: string;
  available: Building[];
  current: Building[];
  setCurrent: (buildings: Building[]) => void;
}

function BuildingTable(props: BuildingTableProps) {
  const [dialogOpen, setDialogOpen] = React.useState(false);

  const columns = [
    {title: "Id", field: "id"},
    {title: "Name", field: "name"},
    {title: "Area", field: "area"},
    {title: "Expertise", field: "expertise"},
    {title: "Pioneers", field: "pioneers"},
    {title: "Settlers", field: "settlers"},
    {title: "Technicians", field: "technicians"},
    {title: "Engineers", field: "engineers"},
    {title: "Scientists", field: "scientists"},
  ];

  const onDelete = (event: Event, rowData: Building | Building[]) => {
    if (rowData instanceof Array) {
      console.log("That was unexpected");
    }
    else {
      const building = rowData as Building;
      const idx = props.current.findIndex(e => e.id === building.id);
      if (idx !== -1) {
        const newCurrent = Array.from(props.current);
        newCurrent.splice(idx, 1);
        props.setCurrent(newCurrent);
      }
    }
  };

  const onOpenDialog = (_event: Event) => {
    setDialogOpen(true);
  };

  const addBuilding = (value: Building | undefined) => {
    setDialogOpen(false);
    if (value !== undefined) {
      const newCurrent = Array.from(props.current);
      newCurrent.push(value);
      props.setCurrent(newCurrent);
    }
  };

  const onDuplicate = (event: Event, rowData: Building | Building[]) => {
    if (rowData instanceof Array) {
      console.log("That was unexpected");
    }
    else {
      const building = rowData as Building;
      addBuilding(JSON.parse(JSON.stringify(building)));
    }
  };

  return <>
    <MaterialTable
      options={{
        paging: false,
        search: false
      }}
      title={props.title}
      columns={columns}
      // This is a hack for https://www.gitmemory.com/issue/mbrn/material-table/893/519018535
      data={props.current.map(building => JSON.parse(JSON.stringify(building)))}
      actions={[
        {
          icon: "addCircle",
          tooltip: "Clone Building",
          onClick: onDuplicate
        },
        {
          icon: "delete",
          tooltip: "Delete Building",
          onClick: onDelete
        },
        {
          icon: "add",
          tooltip: "Add Building",
          isFreeAction: true,
          onClick: onOpenDialog
        }
      ]}
    />
    <AddBuildingDialog open={dialogOpen} onClose={addBuilding} buildings={props.available}/>
  </>;
}

export interface AddBuildingDialogProps {
  open: boolean;
  onClose: (value: Building | undefined) => void;
  buildings: Building[];
}

function AddBuildingDialog(props: AddBuildingDialogProps) {
  const {onClose, buildings, open} = props;
  const [selectedBuilding] = React.useState<Building | undefined>();

  const handleClose = (value: string | undefined = undefined) => {
    if (value !== undefined) {
      const newBuilding = buildings.find(building => building.id === value);
      if (newBuilding) {
        onClose(newBuilding);
      }
    }
    onClose(undefined);
  };

  const handleBuildingSelect = (event: React.ChangeEvent<{ value: unknown }>) => {
    handleClose(event.target.value as string);
  };


  return <Dialog open={open} onClose={() => handleClose()}>
    <DialogTitle>Select Building</DialogTitle>
    <Select
      value={selectedBuilding ? selectedBuilding.id : ""}
      onChange={handleBuildingSelect}>
      {buildings
        .sort((a, b) => a.id.localeCompare(b.id))
        .map(building =>
          <MenuItem key={building.id} value={building.id}>
            {`${building.id} ${building.name !== building.id ? `(${building.name})` : ""}`}
          </MenuItem>)}
    </Select>
  </Dialog>;
}