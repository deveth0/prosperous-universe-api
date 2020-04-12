/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

import * as React from "react";

interface LoadingInfoProps {
  isLoading: boolean;
  isError: boolean;
}

/**
 *
 * @param props set isLoading or error to display an error or loading spinner
 * @returns {null|*}
 * @constructor
 */
export function LoadingInfo(props: LoadingInfoProps): JSX.Element {

  return (
    <div>
      ${props.isError ? "ERROR!!" : "LOADING"}
    </div>
  );
}
