package edu.epowerhouse.reporting.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.epowerhouse.common.models.aggregations.BranchSales;
import edu.epowerhouse.reporting.repositories.BranchReportRepository;

@Service
public class BranchReportService {
    private final BranchReportRepository branchReportRepository;

    public BranchReportService(BranchReportRepository branchReportRepository) {
        this.branchReportRepository = branchReportRepository;
    }

    public List<BranchSales> getBranchesWithMostSales() throws SQLException {
        return branchReportRepository.getBranchesWithMostSales();
    }

    public List<BranchSales> getBranchesWithHighestIncome() throws SQLException {
        return branchReportRepository.branchesWithHighestIncome();
    }
}
