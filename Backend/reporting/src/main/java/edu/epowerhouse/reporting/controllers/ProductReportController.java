package edu.epowerhouse.reporting.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.epowerhouse.common.models.aggregations.ProductSold;
import edu.epowerhouse.reporting.services.ProductReportService;

@RestController
@RequestMapping("/product-reports")
public class ProductReportController {
    private final ProductReportService productReportService;

    public ProductReportController(ProductReportService productReportService) {
        this.productReportService = productReportService;
    }

    @GetMapping("/most-selled")
    public List<ProductSold> getMostSelledProducts() throws SQLException {
        return productReportService.getMostSelledProducts();
    }

    @GetMapping("/highest-income")
    public List<ProductSold> getHighestIncomeProducts() throws SQLException {
        return productReportService.getHighestIncomeProducts();
    }

    @GetMapping("/most-selled/{branchId}")
    public List<ProductSold> getMostSelledProductsByBranch(@PathVariable int branchId) throws SQLException {
        return productReportService.getMostSelledProductsByBranch(branchId);
    }

    @GetMapping("/highest-income/{branchId}")
    public List<ProductSold> getHighestIncomeProductsByBranch(@PathVariable int branchId) throws SQLException {
        return productReportService.getHighestIncomeProductsByBranch(branchId);
    }
}
