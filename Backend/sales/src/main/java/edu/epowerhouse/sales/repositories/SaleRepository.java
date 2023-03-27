package edu.epowerhouse.sales.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.stereotype.Repository;

import edu.epowerhouse.sales.daos.SaleDAO;
import edu.epowerhouse.common.models.aggregations.Invoice;
import edu.epowerhouse.common.models.records.Sale;

@Repository
public class SaleRepository {
    private static final String SELECT_SALE_SQL = "SELECT v.id, v.fecha, v.descuento, e.nombre AS employee_name, "
            + "s.nombre AS branch_name, s.direccion AS branch_address, c.nit AS customer_nit, "
            + "CONCAT(c.nombre, ' ', c.apellido) AS customer_name "
            + "FROM ventas.venta v "
            + "JOIN empleados.empleado e ON v.cui_empleado = e.cui "
            + "JOIN ventas.sucursal s ON v.id_sucursal = s.id "
            + "JOIN ventas.cliente c ON v.nit_cliente = c.nit "
            + "WHERE v.id = ?";

    private static final String SELECT_TOTAL_CLIENT_LAST_SALE = "SELECT SUM(dv.cantidad * p.precio_unidad) AS total "
            + "FROM ventas.venta v"
            + "JOIN ventas.detalle_venta dv ON v.id = dv.id_venta "
            + "JOIN ventas.producto p ON dv.id_producto = p.id "
            + "GROUP BY v.id WHERE v.nit_cliente = ? ORDER BY v.fecha DESC LIMIT 1";

    private final Connection connection;
    private final SaleDetailRepository saleDetailRepository;
    private final SaleDAO saleDAO;

    public SaleRepository(Connection connection) {
        this.connection = connection;
        this.saleDetailRepository = new SaleDetailRepository(connection);
        this.saleDAO = new SaleDAO(connection);
    }

    public double getLastClientSaleTotal(String clientNit) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_TOTAL_CLIENT_LAST_SALE)) {
            statement.setString(1, clientNit);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return -1;
                }

                return resultSet.getDouble("total");
            }
        }
    }

    public Invoice findSaleById(int saleId) throws SQLException {
        try (PreparedStatement saleStatement = connection.prepareStatement(SELECT_SALE_SQL)) {
            saleStatement.setInt(1, saleId);

            try (ResultSet saleResultSet = saleStatement.executeQuery()) {
                if (!saleResultSet.next()) {
                    return null;
                }

                int id = saleResultSet.getInt("id");
                LocalDate date = saleResultSet.getDate("fecha").toLocalDate();
                double discount = saleResultSet.getDouble("descuento");
                String employeeName = saleResultSet.getString("employee_name");
                String branchName = saleResultSet.getString("branch_name");
                String branchAddress = saleResultSet.getString("branch_address");
                String customerNit = saleResultSet.getString("customer_nit");
                String customerName = saleResultSet.getString("customer_name");

                return new Invoice(
                        id,
                        date,
                        discount,
                        0,
                        employeeName,
                        branchName,
                        branchAddress,
                        customerNit,
                        customerName,
                        null);
            }
        }
    }

    public void createSale(Sale sale) throws SQLException {
        try {
            connection.setAutoCommit(false);

            saleDAO.createSale(sale);
            saleDetailRepository.createSaleDetails(sale.saleDetails());

            connection.commit();
        } finally {
            connection.setAutoCommit(true);
        }

    }

    public void updateSale(Sale sale) throws SQLException {
        saleDAO.updateSale(sale);
    }
}
