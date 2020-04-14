/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */


import React from "react";

import {Material} from "./Material";
import "./MaterialList.scss";

interface MaterialListProps {
  materials: string[];
}

export function MaterialList(props: MaterialListProps) {

  return <div className={"pru-m-materiallist"}>
    {props.materials.map(material => <Material key={material} name={material}/>)}
  </div>;
}
