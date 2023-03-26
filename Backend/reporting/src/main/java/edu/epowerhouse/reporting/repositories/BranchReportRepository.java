package edu.epowerhouse.reporting.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import edu.epowerhouse.common.models.aggregations.BranchSales;

@Repository
public class BranchReportRepository {
    private static final String FIND_BRANCH_SALES = "SELECT s.id, s.nombre, s.direccion, s.telefono, SUM(dv.cantidad) AS sales_amount, "
            + "SUM(dv.cantidad * dv.precio_unidad * (1 - v.descuento)) AS total_revenue, SUM(dv.cantidad * dv.precio_unidad * v.descuento) AS discounted "
            + "FROM ventas.venta v "
            + "JOIN ventas.detalle_venta dv ON dv.id_venta = v.id "
            + "JOIN ventas.sucursal s ON s.id = v.id_sucursal GROUP BY s.id, s.nombre, s.direccion, s.telefono ";

    private static final String BRANCHES_WITH_MOST_SALES = FIND_BRANCH_SALES
            + "ORDER BY sales_amount DESC LIMIT 3";

    private static final String BRANCHES_WITH_HIGHEST_INCOME = FIND_BRANCH_SALES
            + "ORDER BY total_revenue DESC LIMIT 3";

    private final Connection connection;

    public BranchReportRepository(Connection connection) {
        this.connection = connection;
    }

    private List<BranchSales> getBranchSalesCustomQuery(String query) throws SQLException {
        List<BranchSales> branchSales = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("nombre");
                String address = resultSet.getString("direccion");
                String phone = resultSet.getString("telefono");
                int salesAmount = resultSet.getInt("sales_amount");
                float totalRevenue = resultSet.getFloat("total_revenue");
                float discounted = resultSet.getFloat("discounted");

                branchSales.add(new BranchSales(id,
                        name,
                        address,
                        phone,
                        salesAmount,
                        totalRevenue,
                        discounted));
            }
        }
        return branchSales;
    }

    public List<BranchSales> getBranchesWithMostSales() throws SQLException {
        return getBranchSalesCustomQuery(BRANCHES_WITH_MOST_SALES);
    }

    public List<BranchSales> branchesWithHighestIncome() throws SQLException {
        return getBranchSalesCustomQuery(BRANCHES_WITH_HIGHEST_INCOME);
    }
}
