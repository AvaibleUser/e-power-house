package edu.epowerhouse.sales.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import edu.epowerhouse.common.models.records.Inventory;

public class InventoryDAO {
    private static final String INSERT_INVENTORY_SQL = "INSERT INTO inventario.inventario (id_bodega, id_producto, cantidad) VALUES (?, ?, ?)";
    private static final String UPDATE_INVENTORY_SQL = "UPDATE inventario.inventario SET cantidad = ? WHERE id_bodega = ? AND id_producto = ?";
    private static final String DELETE_INVENTORY_SQL = "DELETE FROM inventario.inventario WHERE id_bodega = ? AND id_producto = ?";

    private final Connection connection;

    public InventoryDAO(Connection connection) {
        this.connection = connection;
    }

    public void createInventory(Inventory inventory) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_INVENTORY_SQL)) {
            statement.setInt(1, inventory.warehouseId());
            statement.setInt(2, inventory.productId());
            statement.setInt(3, inventory.quantity());

            statement.executeUpdate();
        }
    }

    public void updateInventory(Inventory inventory) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_INVENTORY_SQL)) {
            statement.setInt(1, inventory.quantity());
            statement.setInt(2, inventory.warehouseId());
            statement.setInt(3, inventory.productId());

            statement.executeUpdate();
        }
    }

    public void deleteInventory(int warehouseId, int productId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_INVENTORY_SQL)) {
            statement.setInt(1, warehouseId);
            statement.setInt(2, productId);

            statement.executeUpdate();
        }
    }
}
