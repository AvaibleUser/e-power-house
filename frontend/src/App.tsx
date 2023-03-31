import React from "react";
import "./App.css";
import { createBrowserRouter, RouterProvider } from "react-router-dom";

import {
  getCompleteStock,
  getCompleteStockByBranch,
  getCompleteStockByWarehouse,
} from "./services/stock.service";
import { getAllBranches, getAllWarehouses } from "./services/buildings.service";

import Login from "./routes/login.route";
import Salesmans from "./routes/salesmans.route";
import Sales from "./routes/salesmans/sales.route";
import Inventory from "./routes/inventory.route";
import StockProducts from "./routes/inventory/stock-products.route";
import Admin from "./routes/admin.route";
import Reports from "./routes/admin/reports.route";

const router = createBrowserRouter([
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "salesman",
    element: <Salesmans />,
    children: [{ path: "sales", element: <Sales />, loader: getCompleteStock }],
  },
  {
    path: "inventory",
    element: <Inventory />,
    loader: async () => [
      ...(await getAllBranches()),
      ...(await getAllWarehouses()),
    ],
    children: [
      {
        path: "branch/:branchId",
        element: <StockProducts />,
        loader: getCompleteStockByBranch,
      },
      {
        path: "warehouse/:warehouseId",
        element: <StockProducts />,
        loader: getCompleteStockByWarehouse,
      },
    ],
  },
  {
    path: "admin",
    element: <Admin />,
    children: [
      {
        path: "reports",
        element: <Reports />,
      },
      {
        path: "employees",
        element: <StockProducts />,
      },
    ],
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
