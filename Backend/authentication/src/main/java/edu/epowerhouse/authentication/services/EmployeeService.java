package edu.epowerhouse.authentication.services;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

import edu.epowerhouse.authentication.daos.EmployeeDAO;
import edu.epowerhouse.common.models.records.Employee;

@Service
public class EmployeeService {
    private final EmployeeDAO employeeDAO;

    public EmployeeService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public Employee findEmployee(String cui) throws SQLException {
        return employeeDAO.findEmployee(cui);
    }
}
