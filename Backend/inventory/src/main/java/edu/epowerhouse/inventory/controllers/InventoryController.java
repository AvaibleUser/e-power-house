package edu.epowerhouse.inventory.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.epowerhouse.common.models.aggregations.InventoryItem;
import edu.epowerhouse.common.models.records.Inventory;
import edu.epowerhouse.inventory.services.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;
    
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
    
    @GetMapping("/{warehouseId}")
    public ResponseEntity<List<InventoryItem>> getCompleteInventory(@PathVariable int warehouseId) {
        try {
            List<InventoryItem> inventory = inventoryService.findCompleteInventory(warehouseId);
            if (inventory == null || inventory.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(inventory);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/")
    public ResponseEntity<Void> createInventory(@RequestBody Inventory inventory) {
        try {
            inventoryService.createInventory(inventory);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{warehouse}")
    public ResponseEntity<Void> updateInventory(@PathVariable int warehouse, @RequestBody Inventory inventory) {
        if (warehouse != inventory.warehouseId()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            inventoryService.updateInventory(inventory);
            return ResponseEntity.ok().build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
