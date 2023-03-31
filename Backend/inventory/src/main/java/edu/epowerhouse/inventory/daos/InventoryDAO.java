package edu.epowerhouse.inventory.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import edu.epowerhouse.common.models.records.Inventory;
import edu.epowerhouse.common.utils.DatabaseConnection;

@Component
public class InventoryDAO {
    private static final String INSERT_INVENTORY_SQL = "INSERT INTO inventario.inventario (id_bodega, id_producto, cantidad) VALUES (?, ?, ?)";
    private static final String FIND_INVENTORY_SQL = "SELECT * FROM inventario.inventario WHERE id_bodega = ? AND id_producto = ?";
    private static final String UPDATE_INVENTORY_SQL = "UPDATE inventario.inventario SET cantidad = ? WHERE id_bodega = ? AND id_producto = ?";

    private final Connection connection;

    public InventoryDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void createInventory(Inventory inventory) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_INVENTORY_SQL)) {
            statement.setInt(1, inventory.warehouseId());
            statement.setInt(2, inventory.productId());
            statement.setInt(3, inventory.amount());

            statement.executeUpdate();
        }
    }

    public Inventory findInventory(int warehouseId, int productId) throws SQLException {
        Inventory inventory = null;

        try (PreparedStatement statement = connection.prepareStatement(FIND_INVENTORY_SQL)) {
            statement.setInt(1, warehouseId);
            statement.setInt(2, productId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }
                inventory = new Inventory(
                        resultSet.getInt("id_bodega"),
                        resultSet.getInt("id_producto"),
                        resultSet.getInt("cantidad"));
            }
        }
        return inventory;
    }

    public void updateInventory(Inventory inventory) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_INVENTORY_SQL)) {
            statement.setInt(1, inventory.amount());
            statement.setInt(2, inventory.warehouseId());
            statement.setInt(3, inventory.productId());

            statement.executeUpdate();
        }
    }
}
