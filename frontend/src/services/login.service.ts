import axios from "axios";

import Employee from "../models/employee.model";

const baseUrl = "http://localhost:8081"; //"http://authentication:8080";

export function getPathToRedirect() {
  try {
    const employee = getEmployee();

    switch (employee?.jobTitle?.toString()) {
      case "VENDEDOR":
        return "/salesman";

      case "INVENTARISTA":
        return "/inventory";

      case "BODEGUERO":
        return "/warehouse";

      case "ADMINISTRADOR":
        return "/admin";
    }
  } catch (e: unknown) {}
  return "/login";
}

export function logout() {
  localStorage.removeItem("employee");
}

export async function login(cui: string, password: string) {
  const { data } = await axios.post<Employee>(`${baseUrl}/public/login`, {
    cui,
    password,
  });

  localStorage.setItem("employee", JSON.stringify(data));

  return data;
}

export async function signUp(employee: Employee) {
  const { data } = await axios.post<Employee>(`${baseUrl}/admin/employee/`, employee);

  return data;
}

export function getEmployee(): Employee {
  return JSON.parse(localStorage.getItem("employee") || "{}");
}
