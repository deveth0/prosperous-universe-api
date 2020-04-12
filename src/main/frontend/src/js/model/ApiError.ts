/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

export interface ApiError {
  httpStatus: string;
  message: string;
}

export function isApiError(arg: any): arg is ApiError {
  return arg.httpStatus !== undefined && arg.message !== undefined;
}