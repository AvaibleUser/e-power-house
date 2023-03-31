package edu.epowerhouse.sales.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;

import edu.epowerhouse.common.models.records.Sale;
import edu.epowerhouse.common.utils.DatabaseConnection;

@Component
public class SaleDAO {
    private static final String INSERT_SALE_SQL = "INSERT INTO ventas.venta (cui_empleado, nit_cliente, id_sucursal, fecha, descuento) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SALE_SQL = "UPDATE ventas.venta SET cui_empleado = ?, nit_cliente = ?, id_sucursal = ?, fecha = ?, descuento = ? WHERE id = ?";

    private final Connection connection;

    public SaleDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public long createSale(Sale sale) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_SALE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, sale.employeeCui());
            statement.setString(2, sale.clientNit());
            statement.setInt(3, sale.branchId());
            statement.setDate(4, Date.valueOf(sale.date()));
            statement.setFloat(5, sale.discount());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }

    public void updateSale(Sale sale) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_SALE_SQL)) {
            statement.setString(1, sale.employeeCui());
            statement.setString(2, sale.clientNit());
            statement.setInt(3, sale.branchId());
            statement.setDate(4, Date.valueOf(sale.date()));
            statement.setFloat(5, sale.discount());
            statement.setInt(6, sale.id());

            statement.executeUpdate();
        }
    }
}
