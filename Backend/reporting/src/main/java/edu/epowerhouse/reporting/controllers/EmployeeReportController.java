package edu.epowerhouse.reporting.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.epowerhouse.common.models.aggregations.EmployeeSales;
import edu.epowerhouse.reporting.services.EmployeeReportService;

@RestController
@RequestMapping("/employee-reports")
public class EmployeeReportController {
    private final EmployeeReportService employeeReportService;

    public EmployeeReportController(EmployeeReportService employeeReportService) {
        this.employeeReportService = employeeReportService;
    }

    @GetMapping("/most-sales")
    public List<EmployeeSales> getEmployeesWithMostSales() throws SQLException {
        return employeeReportService.getEmployeesWithMostSales();
    }

    @GetMapping("/highest-income")
    public List<EmployeeSales> getEmployeesWithHighestIncome() throws SQLException {
        return employeeReportService.getEmployeesWithHighestIncome();
    }
}
