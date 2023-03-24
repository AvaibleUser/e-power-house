package edu.epowerhouse.sales.controllers;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.epowerhouse.common.models.aggregations.Invoice;
import edu.epowerhouse.common.models.records.Sale;
import edu.epowerhouse.sales.services.SaleService;

@RestController
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getSaleById(@PathVariable int id) {
        try {
            Invoice invoice = saleService.findSaleById(id);
            if (invoice == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(invoice);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Void> createSale(@RequestBody Sale sale) {
        try {
            saleService.createSale(sale);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
