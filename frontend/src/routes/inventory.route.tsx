import React, { useEffect } from "react";
import { Outlet, useLoaderData, useNavigate } from "react-router-dom";

import Branch from "../models/branch.model";
import Warehouse from "../models/warehouse.model";

import Header from "../modules/header.component";
import { getPathToRedirect } from "../services/login.service";

export default function Inventory() {
  const navigate = useNavigate();

  const items = (useLoaderData() as (Warehouse | Branch)[]).map(
    ({ id, name, type }) => ({ itemName: name, itemUri: `${type}/${id}` })
  );

  useEffect(() => {
    const redirectPath = getPathToRedirect();

    if (redirectPath !== "/inventory") {
      navigate(redirectPath);
    }
  }, []);

  return (
    <>
      <Header title="Inventaristas" items={items} />
      <Outlet />
    </>
  );
}
