import { getAllBranches } from "../services/buildings.service";
import {
  getBranchesWithHighestIncomes,
  getBranchesWithMostSales,
  getEmployeesWithHighestIncomes,
  getEmployeesWithMostSales,
  getHighestIncomeClients,
  getProductsMostSelledByBranch,
  getProductsWithHighestIncomesByBranch,
  getProductsMostSelled,
  getProductsWithHighestIncomes,
} from "../services/reports.service";

const getAllBranchesProductsMostSelled = async () => {
  const branches = await getAllBranches();
  const branchesProductsMostSelled = await Promise.all(
    branches.map((branch) => getProductsMostSelledByBranch(branch.id))
  );

  return branchesProductsMostSelled.flat();
};

const getAllBranchesProductsWithHighestIncomes = async () => {
  const branches = await getAllBranches();
  const branchesProductsWithHighestIncomes = await Promise.all(
    branches.map((branch) => getProductsWithHighestIncomesByBranch(branch.id))
  );

  return branchesProductsWithHighestIncomes.flat();
};

const reportsContructionInfo = [
  {
    category: "Clientes",
    reports: [
      {
        dataLoader: getHighestIncomeClients,
        reportName: "Mayores ganancias generadas",
        headers: [
          "#",
          "Nit",
          "Nombre Completo",
          "Compras Totales",
          "Total Descontado",
          "Ganancias Generadas",
          "Ingresos Totales",
        ],
        attributes: [
          "nit",
          "name",
          "purchasesAmount",
          "totalDiscounted",
          "totalRevenue",
          "totalIncome",
        ],
      },
    ],
  },
  {
    category: "Sucursales",
    reports: [
      {
        dataLoader: getBranchesWithMostSales,
        reportName: "Mayores Ventas",
        headers: [
          "#",
          "Id",
          "Nombre",
          "Direccion",
          "Telefono",
          "Ganancias",
          "Descontado",
          "Ingresos Totales",
          "Cantidad de Ventas",
        ],
        attributes: [
          "id",
          "name",
          "address",
          "phone",
          "totalRevenue",
          "discounted",
          "totalIncome",
          "salesAmount",
        ],
      },
      {
        dataLoader: getBranchesWithHighestIncomes,
        reportName: "Mayores Ingresos",
        headers: [
          "#",
          "Id",
          "Nombre",
          "Direccion",
          "Telefono",
          "Cantidad de Ventas",
          "Descontado",
          "Ganancias",
          "Ingresos Totales",
        ],
        attributes: [
          "id",
          "name",
          "address",
          "phone",
          "salesAmount",
          "discounted",
          "totalRevenue",
          "totalIncome",
        ],
      },
    ],
  },
  {
    category: "Empleados",
    reports: [
      {
        dataLoader: getEmployeesWithMostSales,
        reportName: "Mayores Ventas",
        headers: [
          "#",
          "Cui",
          "Nombre Completo",
          "Nombre de la Sucursal",
          "Ganancias",
          "Descontado",
          "Ingresos Totales",
          "Cantidad de Ventas",
        ],
        attributes: [
          "cui",
          "employeeName",
          "branchName",
          "totalRevenue",
          "totalDiscounted",
          "totalIncome",
          "salesAmount",
        ],
      },
      {
        dataLoader: getEmployeesWithHighestIncomes,
        reportName: "Mayores Ingresos",
        headers: [
          "#",
          "Cui",
          "Nombre Completo",
          "Nombre de la Sucursal",
          "Cantidad de Ventas",
          "Descontado",
          "Ganancias",
          "Ingresos Totales",
        ],
        attributes: [
          "cui",
          "employeeName",
          "branchName",
          "salesAmount",
          "totalDiscounted",
          "totalRevenue",
          "totalIncome",
        ],
      },
    ],
  },
  {
    category: "Productos",
    reports: [
      {
        dataLoader: getProductsMostSelled,
        reportName: "Mas Vendidos",
        headers: [
          "#",
          "Id",
          "Nombre",
          "Descripcion",
          "Descontado",
          "Ganancias",
          "Ingresos Totales",
          "Cantidad de Ventas",
        ],
        attributes: [
          "id",
          "name",
          "description",
          "totalDiscounted",
          "totalRevenue",
          "totalIncome",
          "amount",
        ],
      },
      {
        dataLoader: getProductsWithHighestIncomes,
        reportName: "Mayores Ingresos",
        headers: [
          "#",
          "Id",
          "Nombre",
          "Descripcion",
          "Cantidad de Ventas",
          "Descontado",
          "Ganancias",
          "Ingresos Totales",
        ],
        attributes: [
          "id",
          "name",
          "description",
          "amount",
          "totalDiscounted",
          "totalRevenue",
          "totalIncome",
        ],
      },
      {
        dataLoader: getAllBranchesProductsMostSelled,
        reportName: "Mas Vendidos Por Sucursal",
        headers: [
          "#",
          "Id",
          "Nombre",
          "Descripcion",
          "Descontado",
          "Ganancias",
          "Ingresos Totales",
          "Cantidad de Ventas",
        ],
        attributes: [
          "id",
          "name",
          "description",
          "totalRevenue",
          "totalDiscounted",
          "totalIncome",
          "amount",
        ],
      },
      {
        dataLoader: getAllBranchesProductsWithHighestIncomes,
        reportName: "Mayores Ingresos Por Sucursal",
        headers: [
          "#",
          "Id",
          "Nombre",
          "Descripcion",
          "Cantidad de Ventas",
          "Descontado",
          "Ganancias",
          "Ingresos Totales",
        ],
        attributes: [
          "id",
          "name",
          "description",
          "amount",
          "totalDiscounted",
          "totalRevenue",
          "totalIncome",
        ],
      },
    ],
  },
];

export default reportsContructionInfo;
