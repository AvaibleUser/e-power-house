package edu.epowerhouse.reporting.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import edu.epowerhouse.common.models.aggregations.EmployeeSales;
import edu.epowerhouse.common.utils.DatabaseConnection;

@Repository
public class EmployeeReportRepository {
    private static final String FIND_EMPLOYEE_SALES = "SELECT e.cui, CONCAT(e.nombre, ' ', e.apellido) AS employee_name, s.nombre, SUM(dv.cantidad) AS sales_amount, SUM(dv.cantidad * dv.precio_unidad) "
            + "AS total_income, SUM(dv.cantidad * dv.precio_unidad * (1 - v.descuento)) AS total_revenue, SUM(dv.cantidad * dv.precio_unidad * v.descuento) AS discounted "
            + "FROM empleados.empleado e "
            + "JOIN ventas.venta v ON v.cui_empleado = e.cui "
            + "JOIN ventas.detalle_venta dv ON dv.id_venta = v.id "
            + "JOIN ventas.sucursal s ON s.id = v.id_sucursal GROUP BY e.cui, e.nombre, e.apellido, s.nombre ";

    private static final String EMPLOYEES_WITH_MOST_SALES = FIND_EMPLOYEE_SALES
            + "ORDER BY sales_amount DESC LIMIT 3";

    private static final String EMPLOYEES_WITH_HIGHEST_INCOME = FIND_EMPLOYEE_SALES
            + "ORDER BY total_income DESC LIMIT 3";

    private final Connection connection;

    public EmployeeReportRepository() {
        this.connection = DatabaseConnection.getConnection();
    }

    private List<EmployeeSales> getEmployeesWithCustomQuery(String query) throws SQLException {
        List<EmployeeSales> employeesSales = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String cui = resultSet.getString("cui");
                String employeeName = resultSet.getString("employee_name");
                String branchName = resultSet.getString("nombre");
                int salesAmount = resultSet.getInt("sales_amount");
                float totalRevenue = resultSet.getFloat("total_revenue");
                float totalIncome = resultSet.getFloat("total_income");
                float discounted = resultSet.getFloat("discounted");

                employeesSales.add(new EmployeeSales(
                        cui,
                        employeeName,
                        branchName,
                        salesAmount,
                        totalRevenue,
                        totalIncome,
                        discounted));
            }
        }
        return employeesSales;
    }

    public List<EmployeeSales> getEmployeesWithMostSales() throws SQLException {
        return getEmployeesWithCustomQuery(EMPLOYEES_WITH_MOST_SALES);
    }

    public List<EmployeeSales> getEmployeesWithHighestIncome() throws SQLException {
        return getEmployeesWithCustomQuery(EMPLOYEES_WITH_HIGHEST_INCOME);
    }
}
