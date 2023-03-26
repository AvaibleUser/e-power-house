package edu.epowerhouse.reporting.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import edu.epowerhouse.common.models.aggregations.ProductSold;

@Repository
public class ProductReportRepository {
    private static final String FIND_PRODUCTS_SOLD = "SELECT p.id, p.nombre, p.descripcion, SUM(cantidad) AS amount, SUM(dv.cantidad"
            + " * dv.precio_unidad * (1 - discount)) AS total_revenue, SUM(dv.cantidad * dv.precio_unidad * v.descuento) AS discounted "
            + "FROM ventas.detalle_venta dv "
            + "JOIN ventas.venta v ON dv.id_venta = v.id "
            + "JOIN ventas.producto p ON dv.id_producto = p.id "
            + "GROUP BY p.id, p.nombre, p.descripcion ORDER BY ";

    private static final String MOST_SELLED_PRODUCTS = FIND_PRODUCTS_SOLD
            + "total_vendido DESC LIMIT 10";

    private static final String HIGHEST_INCOME_PRODUCTS = FIND_PRODUCTS_SOLD
            + "totalRevenue DESC LIMIT 10";

    private static final String MOST_SELLED_PRODUCTS_BY_BRANCH = FIND_PRODUCTS_SOLD
            + "total_vendido WHERE v.id_sucursal DESC LIMIT 5";

    private static final String HIGHEST_INCOME_PRODUCTS_BY_BRANCH = FIND_PRODUCTS_SOLD
            + "totalRevenue WHERE v.id_sucursal DESC LIMIT 5";

    private final Connection connection;

    public ProductReportRepository(Connection connection) {
        this.connection = connection;
    }

    private List<ProductSold> reportProducts(String query, int branchId) throws SQLException {
        List<ProductSold> soldProducts = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if (branchId >= 0) {
                statement.setInt(1, branchId);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("nombre");
                    String description = resultSet.getString("descripcion");
                    int amount = resultSet.getInt("amount");
                    float totalRevenue = resultSet.getFloat("total_revenue");
                    float totalDiscounted = resultSet.getFloat("discounted");

                    soldProducts.add(new ProductSold(
                            id,
                            name,
                            description,
                            amount,
                            totalRevenue,
                            totalDiscounted));
                }
            }
        }
        return soldProducts;
    }

    public List<ProductSold> getMostSelledProducts() throws SQLException {
        return reportProducts(MOST_SELLED_PRODUCTS, -1);
    }

    public List<ProductSold> getHighestIncomeProducts() throws SQLException {
        return reportProducts(HIGHEST_INCOME_PRODUCTS, -1);
    }

    public List<ProductSold> getMostSelledProductsByBranch(int branchId) throws SQLException {
        return reportProducts(MOST_SELLED_PRODUCTS_BY_BRANCH, branchId);
    }

    public List<ProductSold> getHighestIncomeProductsByBranch(int branchId) throws SQLException {
        return reportProducts(HIGHEST_INCOME_PRODUCTS_BY_BRANCH, branchId);
    }
}
