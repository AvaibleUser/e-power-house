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
        return employeeDAO.findEmployee(cui); // TODO: Quitar contrase;a, no prioritario
    }

    public Employee findEmployeeJobTitle(Employee employee) throws SQLException {
        String cui = employee.cui();
        Employee employeeFromDb = employeeDAO.findEmployee(cui);
        String encryptedPassword = employee.password(); // TODO: encriptar contrase;a

        if (employeeFromDb == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        if (employeeFromDb.password().strip().equals(encryptedPassword)) {
            return employeeFromDb;
        }
        throw new IllegalArgumentException("La contrasena es incorrecta");
    }
}
