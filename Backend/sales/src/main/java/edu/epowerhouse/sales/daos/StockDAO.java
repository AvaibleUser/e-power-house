package edu.epowerhouse.sales.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import edu.epowerhouse.common.models.records.Stock;

public class StockDAO {
    private static final String INSERT_STOCK_SQL = "INSERT INTO ventas.stock (id_sucursal, id_producto, cantidad) VALUES (?, ?, ?)";
    private static final String UPDATE_STOCK_SQL = "UPDATE ventas.stock SET cantidad = ? WHERE id_sucursal = ? AND id_producto = ?";

    private final Connection connection;

    public StockDAO(Connection connection) {
        this.connection = connection;
    }

    public void createStock(Stock stock) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_STOCK_SQL)) {
            statement.setInt(1, stock.branchId());
            statement.setInt(2, stock.productId());
            statement.setInt(3, stock.quantity());

            statement.executeUpdate();
        }
    }

    public void updateStock(Stock stock) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_STOCK_SQL)) {
            statement.setInt(1, stock.quantity());
            statement.setInt(2, stock.branchId());
            statement.setInt(3, stock.productId());

            statement.executeUpdate();
        }
    }
}
