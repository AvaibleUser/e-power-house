package edu.epowerhouse.inventory.services;

import java.sql.SQLException;
import java.util.List;

import edu.epowerhouse.common.models.aggregations.InventoryItem;
import edu.epowerhouse.common.models.records.Inventory;
import edu.epowerhouse.inventory.repositories.InventoryRepository;

public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<InventoryItem> findCompleteInventory(int warehouseId) throws SQLException {
        return inventoryRepository.findCompleteInventory(warehouseId);
    }

    public void createInventory(Inventory inventory) throws SQLException {
        inventoryRepository.createInventory(inventory);
    }

    public void updateInventory(Inventory inventory) throws SQLException {
        inventoryRepository.updateInventory(inventory);
    }
}
