package edu.epowerhouse.sales.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.epowerhouse.common.models.aggregations.InvoiceLineItem;
import edu.epowerhouse.common.models.records.SaleDetail;

public class SaleDetailRepository {
    private static final String SELECT_SALE_DETAILS = "SELECT p.nombre AS product_name, dv.cantidad AS amount, "
            + "p.precio_unidad AS unit_price, (dv.cantidad * p.precio_unidad) AS total "
            + "FROM ventas.detalle_venta dv "
            + "JOIN ventas.producto p ON dv.id_producto = p.id "
            + "WHERE dv.id_venta = ?";

    private static final String INSERT_SALE_DETAIL = "INSERT INTO ventas.detalle_venta (id_venta, id_producto, cantidad) "
            + "VALUES (?, ?, ?)";

    private final Connection connection;

    public SaleDetailRepository(Connection connection) {
        this.connection = connection;
    }

    public List<InvoiceLineItem> findSaleDetailsBySaleId(int saleId) throws SQLException {
        List<InvoiceLineItem> lineItems = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_SALE_DETAILS)) {
            statement.setInt(1, saleId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String productName = resultSet.getString("product_name");
                    int amount = resultSet.getInt("amount");
                    double unitPrice = resultSet.getDouble("unit_price");
                    double total = resultSet.getDouble("total");

                    lineItems.add(new InvoiceLineItem(productName, amount, unitPrice, total));
                }
            }
        }
        return lineItems;
    }

    public void createSaleDetails(List<SaleDetail> saleDetails) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_SALE_DETAIL)) {
            connection.setAutoCommit(false);

            for (SaleDetail saleDetail : saleDetails) {
                statement.setInt(1, saleDetail.saleId());
                statement.setInt(2, saleDetail.productId());
                statement.setInt(3, saleDetail.amount());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
        }
    }
}
