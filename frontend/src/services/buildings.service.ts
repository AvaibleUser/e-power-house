import axios from "axios";

import Branch from "../models/branch.model";
import Warehouse from "../models/warehouse.model";
import { getEmployee } from "./login.service";

const baseUrl = "http://localhost:8084"; //"http://inventory:8080";

export async function getAllBranches(): Promise<Branch[]> {
  const { branchId } = getEmployee();
  const { data } = await axios.get<Branch[]>(`${baseUrl}/grocer/branch/`);

  return data
    .filter((branch) => branch.id !== branchId)
    .map((branch) => ({ ...branch, type: "branch" }));
}

export async function getAllWarehouses(): Promise<Warehouse[]> {
  const { data } = await axios.get<Warehouse[]>(
    `${baseUrl}/inventory-manager/warehouse/`
  );

  return data.map((warehouse) => ({ ...warehouse, type: "warehouse" }));
}
