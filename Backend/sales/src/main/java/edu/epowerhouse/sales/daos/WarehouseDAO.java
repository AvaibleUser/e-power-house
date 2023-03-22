package edu.epowerhouse.sales.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.epowerhouse.common.models.records.Warehouse;

public class WarehouseDAO {
    private static final String INSERT_WAREHOUSE_SQL = "INSERT INTO inventario.bodega (nombre, direccion, telefono) VALUES (?, ?, ?)";
    private static final String FIND_WAREHOUSE_SQL = "SELECT * FROM inventario.bodega WHERE id = ?";
    private static final String UPDATE_WAREHOUSE_SQL = "UPDATE inventario.bodega SET nombre = ?, direccion = ?, telefono = ? WHERE id = ?";
    private static final String FIND_ALL_WAREHOUSES_SQL = "SELECT * FROM inventario.bodega";

    private final Connection connection;

    public WarehouseDAO(Connection connection) {
        this.connection = connection;
    }

    public void createWarehouse(Warehouse warehouse) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_WAREHOUSE_SQL,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, warehouse.name());
            statement.setString(2, warehouse.address());
            statement.setString(3, warehouse.phone());

            statement.executeUpdate();
        }
    }

    public Warehouse findWarehouse(int id) throws SQLException {
        Warehouse warehouse = null;

        try (PreparedStatement statement = connection.prepareStatement(FIND_WAREHOUSE_SQL)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }
                warehouse = new Warehouse(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("direccion"),
                        resultSet.getString("telefono"));
            }
        }
        return warehouse;
    }

    public void updateWarehouse(Warehouse warehouse) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_WAREHOUSE_SQL)) {
            statement.setString(1, warehouse.name());
            statement.setString(2, warehouse.address());
            statement.setString(3, warehouse.phone());
            statement.setInt(4, warehouse.id());

            statement.executeUpdate();
        }
    }

    public List<Warehouse> findAllWarehouses() throws SQLException {
        List<Warehouse> warehouses = new ArrayList<>();

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(FIND_ALL_WAREHOUSES_SQL)) {
            while (resultSet.next()) {
                Warehouse warehouse = new Warehouse(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("direccion"),
                        resultSet.getString("telefono"));
                warehouses.add(warehouse);
            }
        }
        return warehouses;
    }
}
