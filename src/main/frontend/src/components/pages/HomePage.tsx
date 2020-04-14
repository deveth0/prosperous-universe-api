import React from "react";
import { Link } from "react-router-dom";

export function HomePage(): JSX.Element {
  return <div>
    <h2>PrUn API</h2>

    <ul>
      <li><Link to="/planets">Planets</Link></li>
      <li><Link to="/base">Base Planner</Link></li>
    </ul>
  </div>;
}