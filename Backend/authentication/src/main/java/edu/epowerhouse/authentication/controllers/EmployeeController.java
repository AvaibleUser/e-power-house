package edu.epowerhouse.authentication.controllers;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.epowerhouse.authentication.services.EmployeeService;
import edu.epowerhouse.common.models.records.Employee;

@RestController
@RequestMapping("/public")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee/{cui}")
    public ResponseEntity<Employee> getEmployeeByNit(@PathVariable String cui) {
        try {
            Employee employee = employeeService.findEmployee(cui);
            if (employee == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(employee);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Employee> loginEmployee(@RequestBody Employee employee) {
        try {
            // TODO: Add login
            return ResponseEntity.ok(employee);
        } catch (Exception/* SQLException */ e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
