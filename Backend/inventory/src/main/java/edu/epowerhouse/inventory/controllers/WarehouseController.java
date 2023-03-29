package edu.epowerhouse.inventory.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.epowerhouse.common.models.records.Warehouse;
import edu.epowerhouse.inventory.daos.WarehouseDAO;

@RestController
@RequestMapping("/inventory-manager/warehouse")
public class WarehouseController {
    private final WarehouseDAO warehouseDao;
    
    public WarehouseController(WarehouseDAO warehouseDao) {
        this.warehouseDao = warehouseDao;
    }
    
    @GetMapping("/")
    public ResponseEntity<List<Warehouse>> getAllWarehousees() {
        try {
            List<Warehouse> warehousees = warehouseDao.findAllWarehouses();
            return ResponseEntity.ok(warehousees);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
