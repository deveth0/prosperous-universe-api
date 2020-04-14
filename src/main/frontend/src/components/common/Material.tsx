/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */
import React from "react";
import "./Material.scss";

interface MaterialProps {
  name: string;
}

export function Material(props: MaterialProps) {
  return <div className={`pru-m-material pru-m-material-${props.name.toLowerCase()}`}>
    <div className={"pru-m-material-label"}>
      {props.name}
    </div>
  </div>;
}