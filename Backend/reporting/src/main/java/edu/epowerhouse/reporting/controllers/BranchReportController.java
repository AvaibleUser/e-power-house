package edu.epowerhouse.reporting.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.epowerhouse.common.models.aggregations.BranchSales;
import edu.epowerhouse.reporting.services.BranchReportService;

@RestController
@RequestMapping("/branch-reports")
public class BranchReportController {
    private final BranchReportService branchReportService;

    public BranchReportController(BranchReportService branchReportService) {
        this.branchReportService = branchReportService;
    }

    @GetMapping("/most-sales")
    public List<BranchSales> getBranchesWithMostSales() throws SQLException {
        System.out.println("log: in controller method most-sales");
        return branchReportService.getBranchesWithMostSales();
    }

    @GetMapping("/highest-income")
    public List<BranchSales> getBranchesWithHighestIncome() throws SQLException {
        System.out.println("log: in controller method highest-income");
        return branchReportService.getBranchesWithHighestIncome();
    }
}
