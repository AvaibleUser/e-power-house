package edu.epowerhouse.reporting.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.epowerhouse.common.models.aggregations.EmployeeSales;
import edu.epowerhouse.reporting.repositories.EmployeeReportRepository;

@Service
public class EmployeeReportService {
    private final EmployeeReportRepository employeeReportRepository;

    public EmployeeReportService(EmployeeReportRepository employeeReportRepository) {
        this.employeeReportRepository = employeeReportRepository;
    }

    public List<EmployeeSales> getEmployeesWithMostSales() throws SQLException {
        return employeeReportRepository.getEmployeesWithMostSales();
    }

    public List<EmployeeSales> getEmployeesWithHighestIncome() throws SQLException {
        return employeeReportRepository.getEmployeesWithHighestIncome();
    }
}
