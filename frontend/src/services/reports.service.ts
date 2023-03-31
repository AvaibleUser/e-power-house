import axios from "axios";

const baseUrl = "http://localhost:8082"; //"http://reports:8080";

export async function getHighestIncomeClients(): Promise<any[]> {
  const { data } = await axios.get<any[]>(`${baseUrl}/client-reports/highest-income`);

  return data;
}

export async function getBranchesWithMostSales(): Promise<any[]> {
  const { data } = await axios.get<any[]>(`${baseUrl}/branch-reports/most-sales`);

  return data;
}

export async function getBranchesWithHighestIncomes(): Promise<any[]> {
  const { data } = await axios.get<any[]>(`${baseUrl}/branch-reports/highest-income`);

  return data;
}

export async function getEmployeesWithMostSales(): Promise<any[]> {
  const { data } = await axios.get<any[]>(`${baseUrl}/employee-reports/most-sales`);

  return data;
}

export async function getEmployeesWithHighestIncomes(): Promise<any[]> {
  const { data } = await axios.get<any[]>(`${baseUrl}/employee-reports/highest-income`);

  return data;
}

export async function getProductsMostSelled(): Promise<any[]> {
  const { data } = await axios.get<any[]>(`${baseUrl}/product-reports/most-selled`);

  return data;
}

export async function getProductsWithHighestIncomes(): Promise<any[]> {
  const { data } = await axios.get<any[]>(`${baseUrl}/product-reports/highest-income`);

  return data;
}

export async function getProductsMostSelledByBranch(branchId: number): Promise<any[]> {
  const { data } = await axios.get<any[]>(`${baseUrl}/product-reports/most-selled/${branchId}`);

  return data;
}

export async function getProductsWithHighestIncomesByBranch(branchId: number): Promise<any[]> {
  const { data } = await axios.get<any[]>(`${baseUrl}/product-reports/highest-income/${branchId}`);

  return data;
}
