import React, { useEffect } from "react";
import { Outlet, useNavigate } from "react-router-dom";

import Header from "../modules/header.component";
import { getPathToRedirect } from "../services/login.service";

const items = [
  { itemName: "Ventas", itemUri: "sales" },
];

export default function Salesmans() {
  const navigate = useNavigate();

  useEffect(() => {
    const redirectPath = getPathToRedirect();
  
    if (redirectPath !== "/salesman") {
      navigate(redirectPath);
    }
  }, [])

  return (
    <>
      <Header title="Vendedores" items={items} />
      <Outlet />
    </>
  );
}
