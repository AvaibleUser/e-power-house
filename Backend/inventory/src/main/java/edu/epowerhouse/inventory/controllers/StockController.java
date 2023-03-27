package edu.epowerhouse.inventory.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.epowerhouse.common.models.aggregations.StockItem;
import edu.epowerhouse.common.models.records.Inventory;
import edu.epowerhouse.common.models.records.Stock;
import edu.epowerhouse.inventory.services.StockService;

@RestController
@RequestMapping("/grocer/stocks")
public class StockController {
    private final StockService stockService;
    
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }
    
    @PostMapping("/")
    public ResponseEntity<Void> createStock(@RequestBody Stock stock) {
        try {
            stockService.createStock(stock);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/")
    public ResponseEntity<List<StockItem>> getCompleteStock(@RequestParam int branchId) {
        try {
            List<StockItem> completeStock = stockService.findCompleteStock(branchId);
            if (completeStock == null || completeStock.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(completeStock);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/shipment/branch/{targetBranchId}")
    public ResponseEntity<Void> updateStockFromBranch(@PathVariable int targetBranchId, @RequestBody Stock stockForTransfer) {
        try {
            stockService.updateStockFromBranch(targetBranchId, stockForTransfer);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.badRequest().build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/shipment/warehouse/{targetBranchId}")
    public ResponseEntity<Void> updateStockFromWarehouse(@PathVariable int targetBranchId, @RequestBody Inventory inventoryForTransfer) {
        try {
            stockService.updateStockFromWarehouse(targetBranchId, inventoryForTransfer);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.badRequest().build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
