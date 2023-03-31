package edu.epowerhouse.inventory.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import edu.epowerhouse.common.models.records.Stock;
import edu.epowerhouse.common.utils.DatabaseConnection;

@Component
public class StockDAO {
    private static final String INSERT_STOCK_SQL = "INSERT INTO ventas.stock (id_sucursal, id_producto, cantidad) VALUES (?, ?, ?)";
    private static final String FIND_STOCK_SQL = "SELECT * FROM ventas.stock WHERE id_sucursal = ? AND id_producto = ?";
    private static final String UPDATE_STOCK_SQL = "UPDATE ventas.stock SET cantidad = ? WHERE id_sucursal = ? AND id_producto = ?";

    private final Connection connection;

    public StockDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void createStock(Stock stock) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_STOCK_SQL)) {
            statement.setInt(1, stock.branchId());
            statement.setInt(2, stock.productId());
            statement.setInt(3, stock.amount());

            statement.executeUpdate();
        }
    }

    public Stock findStock(int branchId, int productId) throws SQLException {
        Stock inventory = null;

        try (PreparedStatement statement = connection.prepareStatement(FIND_STOCK_SQL)) {
            statement.setInt(1, branchId);
            statement.setInt(2, productId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }
                inventory = new Stock(
                        resultSet.getInt("id_sucursal"),
                        resultSet.getInt("id_producto"),
                        resultSet.getInt("cantidad"));
            }
        }
        return inventory;
    }

    public void updateStock(Stock stock) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_STOCK_SQL)) {
            statement.setInt(1, stock.amount());
            statement.setInt(2, stock.branchId());
            statement.setInt(3, stock.productId());

            statement.executeUpdate();
        }
    }
}
