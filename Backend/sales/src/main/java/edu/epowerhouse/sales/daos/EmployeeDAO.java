package edu.epowerhouse.sales.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.epowerhouse.common.models.records.Employee;

public class EmployeeDAO {
    private static final String INSERT_EMPLOYEE_SQL = "INSERT INTO empleados.empleado (cui, nombre, apellido, contrasena, id_sucursal, id_bodega, puesto_trabajo, fecha_nacimiento, direccion, correo_electronico, telefono) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_EMPLOYEE_SQL = "UPDATE empleados.empleado SET nombre = ?, apellido = ?, contrasena = ?, id_sucursal = ?, id_bodega = ?, puesto_trabajo = ?, fecha_nacimiento = ?, direccion = ?, correo_electronico = ?, telefono = ? WHERE cui = ?";
    private static final String DELETE_EMPLOYEE_SQL = "DELETE FROM empleados.empleado WHERE cui = ?";

    private final Connection connection;

    public EmployeeDAO(Connection connection) {
        this.connection = connection;
    }

    public void createEmployee(Employee employee) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_EMPLOYEE_SQL)) {
            statement.setString(1, employee.cui());
            statement.setString(2, employee.name());
            statement.setString(3, employee.lastName());
            statement.setString(4, employee.password());
            statement.setInt(5, employee.branchId());
            statement.setInt(6, employee.warehouseId());
            statement.setString(7, employee.jobTitle().name());
            statement.setDate(8, java.sql.Date.valueOf(employee.birthdate()));
            statement.setString(9, employee.address());
            statement.setString(10, employee.email());
            statement.setString(11, employee.phone());

            statement.executeUpdate();
        }
    }

    public void updateEmployee(Employee employee) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_EMPLOYEE_SQL)) {
            statement.setString(1, employee.name());
            statement.setString(2, employee.lastName());
            statement.setString(3, employee.password());
            statement.setInt(4, employee.branchId());
            statement.setInt(5, employee.warehouseId());
            statement.setString(6, employee.jobTitle().name());
            statement.setDate(7, java.sql.Date.valueOf(employee.birthdate()));
            statement.setString(8, employee.address());
            statement.setString(9, employee.email());
            statement.setString(10, employee.phone());
            statement.setString(11, employee.cui());

            statement.executeUpdate();
        }
    }

    public void deleteEmployee(String cui) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_EMPLOYEE_SQL)) {
            statement.setString(1, cui);

            statement.executeUpdate();
        }
    }
}