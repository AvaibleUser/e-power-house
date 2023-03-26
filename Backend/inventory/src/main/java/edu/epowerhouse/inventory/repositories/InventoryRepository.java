package edu.epowerhouse.inventory.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.epowerhouse.common.models.aggregations.InventoryItem;
import edu.epowerhouse.common.models.records.Inventory;
import edu.epowerhouse.inventory.daos.InventoryDAO;

public class InventoryRepository {
    private static final String FIND_WAREHOUSE_INVENTORY_SQL = "SELECT b.id, b.nombre AS bodega_name, p.nombre AS product_name, p.unit_price, "
            + "p.unit_cost, i.cantidad, p.descripcion FROM inventario.inventario i JOIN inventario.bodega b ON b.id = i.id_bodega"
            + "JOIN ventas.producto p ON p.id = i.id_producto WHERE i.id_bodega = ?;";

    private final Connection connection;
    private final InventoryDAO inventoryDAO;

    public InventoryRepository(Connection connection) {
        this.connection = connection;
        this.inventoryDAO = new InventoryDAO(connection);
    }

    public Inventory findInventory(int warehouseId, int productId) throws SQLException {
        return inventoryDAO.findInventory(warehouseId, productId);
    }

    public List<InventoryItem> findCompleteInventory(int warehouseId) throws SQLException {
        List<InventoryItem> inventory = new ArrayList<>();
    
        try (PreparedStatement inventoryStatement = connection.prepareStatement(FIND_WAREHOUSE_INVENTORY_SQL)) {
            inventoryStatement.setInt(1, warehouseId);

            try (ResultSet inventoryResultSet = inventoryStatement.executeQuery()) {
                while (inventoryResultSet.next()) {
                    int id = inventoryResultSet.getInt("id");
                    String warehouseName = inventoryResultSet.getString("bodega_name");
                    String productName = inventoryResultSet.getString("product_name");
                    double unitPrice = inventoryResultSet.getDouble("unit_price");
                    double unitCost = inventoryResultSet.getDouble("unit_cost");
                    int amount = inventoryResultSet.getInt("cantidad");
                    String description = inventoryResultSet.getString("descripcion");
    
                    inventory.add(new InventoryItem(id, warehouseName, productName, unitPrice, unitCost, amount, description));
                }
            }
        }
    
        return inventory;
    }

    public void createInventory(Inventory inventory) throws SQLException {
        inventoryDAO.createInventory(inventory);
    }

    public void updateInventory(Inventory inventory) throws SQLException {
        inventoryDAO.updateInventory(inventory);
    }
}
