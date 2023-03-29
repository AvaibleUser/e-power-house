import axios from "axios";
import Sale from "../models/sale.model";
import { getEmployee } from "./login.service";

const baseUrl = "http://localhost:8083"; //"http://sales:8080";

export async function addSale(sale: Sale): Promise<void> {
  const { cui: employeeCui } = getEmployee();
  const { data } = await axios.post(`${baseUrl}/sales/`, {
    ...sale,
    employeeCui,
  });

  return data;
}
