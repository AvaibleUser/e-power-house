package edu.epowerhouse.reporting.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.epowerhouse.common.models.aggregations.ProductSold;
import edu.epowerhouse.reporting.repositories.ProductReportRepository;

@Service
public class ProductReportService {
    private final ProductReportRepository productReportRepository;

    public ProductReportService(ProductReportRepository productReportRepository) {
        this.productReportRepository = productReportRepository;
    }

    public List<ProductSold> getMostSelledProducts() throws SQLException {
        return productReportRepository.getMostSelledProducts();
    }

    public List<ProductSold> getHighestIncomeProducts() throws SQLException {
        return productReportRepository.getHighestIncomeProducts();
    }

    public List<ProductSold> getMostSelledProductsByBranch(int branchId) throws SQLException {
        return productReportRepository.getMostSelledProductsByBranch(branchId);
    }

    public List<ProductSold> getHighestIncomeProductsByBranch(int branchId) throws SQLException {
        return productReportRepository.getHighestIncomeProductsByBranch(branchId);
    }
}
