package edu.epowerhouse.sales.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import edu.epowerhouse.common.models.records.Client;
import edu.epowerhouse.common.utils.DatabaseConnection;

@Component
public class ClientDAO {
    private static final String INSERT_CLIENT_SQL = "INSERT INTO ventas.cliente (nit, nombre, apellido, direccion, telefono, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String FIND_CLIENT_SQL = "SELECT * FROM ventas.cliente WHERE nit = ?";
    private static final String UPDATE_CLIENT_SQL = "UPDATE ventas.cliente SET nombre = ?, apellido = ?, direccion = ?, telefono = ?, fecha_nacimiento = ? WHERE nit = ?";

    private final Connection connection;

    public ClientDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void createClient(Client client) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CLIENT_SQL)) {
            statement.setString(1, client.nit());
            statement.setString(2, client.name());
            statement.setString(3, client.lastName());
            statement.setString(4, client.address());
            statement.setString(5, client.phone());
            statement.setDate(6, Date.valueOf(client.birthdate()));

            statement.executeUpdate();
        }
    }

    public Client findClient(String nit) throws SQLException {
        Client client = null;

        try (PreparedStatement statement = connection.prepareStatement(FIND_CLIENT_SQL)) {
            statement.setString(1, nit);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }
                client = new Client(
                        resultSet.getString("nit"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido"),
                        resultSet.getString("direccion"),
                        resultSet.getString("telefono"),
                        resultSet.getDate("fecha_nacimiento").toLocalDate());
            }
        }
        return client;
    }

    public void updateClient(Client client) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CLIENT_SQL)) {
            statement.setString(1, client.name());
            statement.setString(2, client.lastName());
            statement.setString(3, client.address());
            statement.setString(4, client.phone());
            statement.setDate(5, Date.valueOf(client.birthdate()));
            statement.setString(6, client.nit());

            statement.executeUpdate();
        }
    }
}
