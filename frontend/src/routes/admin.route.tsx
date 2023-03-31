import React, { useEffect } from "react";
import { useNavigate, Outlet } from "react-router-dom";

import Header from "../modules/header.component";
import { getPathToRedirect } from "../services/login.service";

const items = [
  { itemName: "Reportes", itemUri: "reports" },
  { itemName: "Empleados", itemUri: "employees" },
];

export default function Admin() {
  const navigate = useNavigate();

  useEffect(() => {
    const redirectPath = getPathToRedirect();

    if (redirectPath !== "/inventory") {
      //navigate(redirectPath);
    }
  }, []);

  return (
    <>
      <Header title="Administrador" items={items} />
      <Outlet />
    </>
  );
}
