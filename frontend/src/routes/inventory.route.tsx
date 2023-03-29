import React, { useEffect } from "react";
import { Link, Outlet, useLoaderData, useNavigate } from "react-router-dom";
import Branch from "../models/branch.model";
import Warehouse from "../models/warehouse.model";

import Header from "../modules/header.component";
import { getPathToRedirect } from "../services/login.service";

const items = [{ itemName: "Salir de cuenta", itemUri: "/login" }];

export default function Inventory() {
  const navigate = useNavigate();

  const buildings = (
    <div className="tabs w-full flex justify-evenly bg-base-300">
      {(useLoaderData() as (Warehouse | Branch)[]).map((building) => (
        <Link
          key={building.id}
          className="tab tab-bordered"
          to={`${building.type}/${building.id}`}
        >
          {building.name}
        </Link>
      ))}
    </div>
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
      {buildings}
      <Outlet />
    </>
  );
}
