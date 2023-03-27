package edu.epowerhouse.authentication.services;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

import edu.epowerhouse.authentication.daos.EmployeeDAO;
import edu.epowerhouse.common.models.records.Employee;

@Service
public class EmployeeManageService {
    private final EmployeeDAO employeeDAO;

    public EmployeeManageService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public void createEmployee(Employee employee) throws SQLException {
        int branchId = employee.branchId();
        int warehouseId = employee.warehouseId();
        String password = employee.password(); // TODO: Encrypt the password

        switch (employee.jobTitle()) {
            case VENDEDOR:
                warehouseId = 0;
                if (employee.branchId() < 1) {
                    throw new IllegalArgumentException("The salesman must be assigned to a branch");
                }
                break;

            case INVENTARISTA:
                warehouseId = 0;
                if (employee.branchId() < 1) {
                    throw new IllegalArgumentException("The inventory employee must be assigned to a branch");
                }
                break;

            case BODEGUERO:
                branchId = 0;
                if (employee.warehouseId() < 1) {
                    throw new IllegalArgumentException("The warehouse employee must be assigned to a warehouse");
                }
                break;

            case ADMINISTRADOR:
                branchId = 0;
                warehouseId = 0;

            default:
                break;
        }

        employee = new Employee(
                employee.cui(),
                employee.name(),
                employee.lastName(),
                password,
                branchId,
                warehouseId,
                employee.jobTitle(),
                employee.birthdate(),
                employee.address(),
                employee.email(),
                employee.phone());

        employeeDAO.createEmployee(employee);
    }

    public void updateEmployee(Employee employee) throws SQLException {
        employeeDAO.updateEmployee(employee);
    }
}
