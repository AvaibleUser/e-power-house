import React from "react";
import { Link } from "react-router-dom";

import ItemModel from "../models/item.model";

export default function ItemComponent({ itemName, itemUri }: ItemModel) {
  return (
    <li>
      <Link to={itemUri}>{itemName}</Link>
    </li>
  );
}
