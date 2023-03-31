package edu.epowerhouse.authentication.controllers;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.epowerhouse.authentication.services.EmployeeManageService;
import edu.epowerhouse.common.models.records.Employee;

@RestController
@RequestMapping("/admin/employee")
public class EmployeeManageController {
    private final EmployeeManageService employeeManageService;

    public EmployeeManageController(EmployeeManageService employeeManageService) {
        this.employeeManageService = employeeManageService;
    }

    @PostMapping("/")
    public ResponseEntity<Void> createEmployee(@RequestBody Employee employee) {
        try {
            employeeManageService.createEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{cui}")
    public ResponseEntity<Void> updateEmployee(@PathVariable String cui, @RequestBody Employee employee) {
        if (!cui.equals(employee.cui())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            employeeManageService.updateEmployee(employee);
            return ResponseEntity.ok().build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
