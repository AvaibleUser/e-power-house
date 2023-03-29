import React from "react";
import { Link } from "react-router-dom";

import HeaderModel from "../models/header.model";
import { getPathToRedirect } from "../services/login.service";
import ItemComponent from "./item.component";

export default function Header({ title, items }: HeaderModel) {
  const itemsInComponent = items.map(({ itemName, itemUri }) => (
    <ItemComponent key={itemName} itemName={itemName} itemUri={itemUri} />
  ));

  return (
    <nav className="navbar bg-base-100">
      <div className="flex-1">
        <Link
          to={getPathToRedirect()}
          className="btn btn-ghost normal-case text-xl"
        >
          {title}
        </Link>
      </div>
      <div className="flex-none">
        <ul className="menu menu-horizontal px-1">{itemsInComponent}</ul>
      </div>
    </nav>
  );
}
