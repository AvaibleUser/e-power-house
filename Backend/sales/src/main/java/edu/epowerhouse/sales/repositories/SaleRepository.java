package edu.epowerhouse.sales.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import edu.epowerhouse.sales.daos.SaleDAO;
import edu.epowerhouse.common.models.aggregations.Invoice;
import edu.epowerhouse.common.models.aggregations.InvoiceLineItem;
import edu.epowerhouse.common.models.records.Sale;

@Repository
public class SaleRepository {
    private static final String SELECT_SALE_SQL = "SELECT v.id, v.fecha, v.descuento, e.nombre AS employeeName, "
            + "s.nombre AS branchName, s.direccion AS branchAddress, c.nit AS customerNit, "
            + "CONCAT(c.nombre, ' ', c.apellido) AS customerName "
            + "FROM ventas.venta v "
            + "JOIN empleados.empleado e ON v.cui_empleado = e.cui "
            + "JOIN ventas.sucursal s ON v.id_sucursal = s.id "
            + "JOIN ventas.cliente c ON v.nit_cliente = c.nit "
            + "WHERE v.id = ?";

    private static final String SELECT_SALE_DETAILS = "SELECT p.nombre AS productName, dv.cantidad AS quantity, "
            + "p.precio_unidad AS unitPrice, (dv.cantidad * p.precio_unidad) AS total "
            + "FROM ventas.detalle_venta dv "
            + "JOIN ventas.producto p ON dv.id_producto = p.id "
            + "WHERE dv.id_venta = ?";

    private final Connection connection;
    private final SaleDAO saleDAO;

    public SaleRepository(Connection connection) {
        this.connection = connection;
        this.saleDAO = new SaleDAO(connection);
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
                String employeeName = saleResultSet.getString("employeeName");
                String branchName = saleResultSet.getString("branchName");
                String branchAddress = saleResultSet.getString("branchAddress");
                String customerNit = saleResultSet.getString("customerNit");
                String customerName = saleResultSet.getString("customerName");

                List<InvoiceLineItem> lineItems = new ArrayList<>();
                try (PreparedStatement lineItemsStatement = connection.prepareStatement(SELECT_SALE_DETAILS)) {
                    lineItemsStatement.setInt(1, id);

                    try (ResultSet lineItemsResultSet = lineItemsStatement.executeQuery()) {
                        while (lineItemsResultSet.next()) {
                            String productName = lineItemsResultSet.getString("productName");
                            int quantity = lineItemsResultSet.getInt("quantity");
                            double unitPrice = lineItemsResultSet.getDouble("unitPrice");
                            double total = lineItemsResultSet.getDouble("total");

                            lineItems.add(new InvoiceLineItem(productName, quantity, unitPrice, total));
                        }
                    }
                }

                double total = lineItems.stream().mapToDouble(InvoiceLineItem::total).sum();

                return new Invoice(id, date, discount, total, employeeName, branchName, branchAddress, customerNit,
                        customerName, lineItems);
            }
        }
    }

    public void createSale(Sale sale) throws SQLException {
        saleDAO.createSale(sale);
    }

    public void updateSale(Sale sale) throws SQLException {
        saleDAO.updateSale(sale);
    }
}
