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
    public ResponseEntity<Float> createSale(@RequestBody Sale sale) {
        try {
            float saleDiscount = saleService.createSale(sale);
            System.out.println("La venta se hizo, descuento " + saleDiscount);
            return ResponseEntity.status(HttpStatus.CREATED).body(saleDiscount);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
