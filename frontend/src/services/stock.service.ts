import axios from "axios";
import Product from "../models/product.model";
import Stock from "../models/stock.model";
import { getEmployee } from "./login.service";

const baseUrl = "http://localhost:8084"; //"http://inventory:8080";

export async function getCompleteStock(): Promise<Product[]> {
  const { branchId } = getEmployee();
  const { data } = await axios.get(`${baseUrl}/grocer/stocks/`, {
    params: { branchId },
  });

  return data;
}

export async function getCompleteStockByBranch({
  params: { branchId },
}: any): Promise<any> {
  const { data } = await axios.get(`${baseUrl}/grocer/stocks/`, {
    params: { branchId },
  });

  return { branchId, data };
}

export async function getCompleteStockByWarehouse({
  params: { warehouseId },
}: any): Promise<any> {
  const { data } = await axios.get(`${baseUrl}/inventory-manager/inventory/`, {
    params: { warehouseId },
  });

  return { warehouseId, data };
}

export async function putStockFromBranch(stock: Stock): Promise<void> {
  const { branchId: targetBranch } = getEmployee();
  const { data } = await axios.put(
    `${baseUrl}/grocer/stocks/shipment/branch/${targetBranch}`,
    stock
  );

  return data;
}
