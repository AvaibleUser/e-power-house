package edu.epowerhouse.authentication.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import edu.epowerhouse.common.models.enums.JobTitle;
import edu.epowerhouse.common.models.records.Employee;

@Component
public class EmployeeDAO {
    private static final String INSERT_EMPLOYEE_SQL = "INSERT INTO empleados.empleado (cui, nombre, apellido, contrasena, id_sucursal, id_bodega, puesto_trabajo, fecha_nacimiento, direccion, correo_electronico, telefono) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_EMPLOYEE_SQL = "SELECT * FROM empleados.empleado WHERE cui = ?";
    private static final String UPDATE_EMPLOYEE_SQL = "UPDATE empleados.empleado SET nombre = ?, apellido = ?, contrasena = ?, id_sucursal = ?, id_bodega = ?, puesto_trabajo = ?, fecha_nacimiento = ?, direccion = ?, correo_electronico = ?, telefono = ? WHERE cui = ?";

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

    public Employee findEmployee(String cui) throws SQLException {
        Employee employee = null;

        try (PreparedStatement statement = connection.prepareStatement(FIND_EMPLOYEE_SQL)) {
            statement.setString(1, cui);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }
                employee = new Employee(
                        resultSet.getString("cui"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido"),
                        resultSet.getString("contrasena"),
                        resultSet.getInt("id_sucursal"),
                        resultSet.getInt("id_bodega"),
                        JobTitle.valueOf(resultSet.getString("puesto_trabajo").toUpperCase()),
                        resultSet.getDate("fecha_nacimiento").toLocalDate(),
                        resultSet.getString("direccion"),
                        resultSet.getString("correo_electronico"),
                        resultSet.getString("telefono"));
            }
        }
        return employee;
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
}