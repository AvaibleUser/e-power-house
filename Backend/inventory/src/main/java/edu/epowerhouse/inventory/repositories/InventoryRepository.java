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
    private static final String FIND_WAREHOUSE_INVENTORY_SQL = "SELECT b.nombre AS bodega_name, p.nombre AS producto_nombre, p.precio_unidad, "
            + "p.costo_unidad, i.cantidad, p.descripcion FROM inventario.inventario i JOIN inventario.bodega b ON b.id = i.id_bodega"
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
                    String warehouseName = inventoryResultSet.getString("bodega_name");
                    String productName = inventoryResultSet.getString("producto_nombre");
                    double unitPrice = inventoryResultSet.getDouble("precio_unidad");
                    double unitCost = inventoryResultSet.getDouble("costo_unidad");
                    int quantity = inventoryResultSet.getInt("cantidad");
                    String description = inventoryResultSet.getString("descripcion");
    
                    inventory.add(new InventoryItem(warehouseName, productName, unitPrice, unitCost, quantity, description));
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
