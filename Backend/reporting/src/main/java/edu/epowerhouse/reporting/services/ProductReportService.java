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

    public List<ProductSold> reportBestSellingProducts() throws SQLException {
        return productReportRepository.reportBestSellingProducts();
    }

    public List<ProductSold> reportBestRevenueProducts() throws SQLException {
        return productReportRepository.reportBestRevenueProducts();
    }

    public List<ProductSold> reportBestSellingProductsByBranch(int branchId) throws SQLException {
        return productReportRepository.reportBestSellingProductsByBranch(branchId);
    }

    public List<ProductSold> reportBestRevenueProductsByBranch(int branchId) throws SQLException {
        return productReportRepository.reportBestRevenueProductsByBranch(branchId);
    }
}
