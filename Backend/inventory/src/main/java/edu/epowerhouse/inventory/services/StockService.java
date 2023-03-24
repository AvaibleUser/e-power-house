package edu.epowerhouse.inventory.services;

import java.sql.SQLException;
import java.util.List;

import edu.epowerhouse.common.models.aggregations.StockItem;
import edu.epowerhouse.common.models.records.Inventory;
import edu.epowerhouse.common.models.records.Stock;
import edu.epowerhouse.inventory.repositories.InventoryRepository;
import edu.epowerhouse.inventory.repositories.StockRepository;

public class StockService {
    private final StockRepository stockRepository;
    private final InventoryRepository inventoryRepository;

    public StockService(StockRepository stockRepository, InventoryRepository inventoryRepository) {
        this.stockRepository = stockRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public void createStock(Stock stock) throws SQLException {
        stockRepository.createStock(stock);
    }

    public List<StockItem> findCompleteStock(int branchId) throws SQLException {
        return stockRepository.findCompleteStock(branchId);
    }

    public void updateStockFromBranch(int targetBranchId, Stock stockForTransfer) throws SQLException {
        int productId = stockForTransfer.productId();
        int sourceBranchId = stockForTransfer.branchId();
        int transferQuantity = stockForTransfer.quantity();

        if (targetBranchId == sourceBranchId) {
            throw new IllegalArgumentException("Cannot made shipment from the same branch.");
        }

        Stock sourceStock = stockRepository.findStock(sourceBranchId, productId);
        if (sourceStock == null) {
            throw new IllegalArgumentException("Source stock not found.");
        }

        int newSourceQuantity = sourceStock.quantity() - transferQuantity;
        if (newSourceQuantity < 0) {
            throw new IndexOutOfBoundsException("Not enough quantity in the source stock.");
        }
        
        Stock targetStock = stockRepository.findStock(targetBranchId, productId);
        if (targetStock == null) {
            throw new IllegalArgumentException("Target stock not found.");
        }

        sourceStock = new Stock(sourceBranchId, productId, newSourceQuantity);
        stockRepository.updateStock(sourceStock);
        
        int newTargetQuantity = targetStock.quantity() + transferQuantity;
        targetStock = new Stock(targetBranchId, productId, newTargetQuantity);
        stockRepository.updateStock(targetStock);
    }

    public void updateStockFromWarehouse(int targetBranchId, Inventory inventoryForTransfer) throws SQLException {
        int productId = inventoryForTransfer.productId();
        int transferQuantity = inventoryForTransfer.quantity();
        int sourceWarehouseId = inventoryForTransfer.warehouseId();

        Inventory sourceInventory = inventoryRepository.findInventory(sourceWarehouseId, productId);
        if (sourceInventory == null) {
            throw new IllegalArgumentException("Source inventory not found.");
        }

        int newSourceQuantity = sourceInventory.quantity() - transferQuantity;
        if (newSourceQuantity < 0) {
            throw new IndexOutOfBoundsException("Not enough quantity in the source inventory.");
        }
        
        Stock targetStock = stockRepository.findStock(targetBranchId, productId);
        if (targetStock == null) {
            throw new IllegalArgumentException("Target stock not found.");
        }

        sourceInventory = new Inventory(sourceWarehouseId, productId, newSourceQuantity);
        inventoryRepository.updateInventory(sourceInventory);
        
        int newTargetQuantity = targetStock.quantity() + transferQuantity;
        targetStock = new Stock(targetBranchId, productId, newTargetQuantity);
        stockRepository.updateStock(targetStock);
    }
}
