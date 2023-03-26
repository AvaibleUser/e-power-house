package edu.epowerhouse.sales.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import edu.epowerhouse.sales.daos.EmployeeDAO;
import edu.epowerhouse.sales.daos.SaleDAO;
import edu.epowerhouse.common.models.aggregations.Invoice;
import edu.epowerhouse.common.models.aggregations.InvoiceLineItem;
import edu.epowerhouse.common.models.enums.JobTitle;
import edu.epowerhouse.common.models.records.Employee;
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

    private static final String SELECT_SALE_DETAILS = "SELECT p.nombre AS product_name, dv.cantidad AS amount, "
            + "p.precio_unidad AS unit_price, (dv.cantidad * p.precio_unidad) AS total "
            + "FROM ventas.detalle_venta dv "
            + "JOIN ventas.producto p ON dv.id_producto = p.id "
            + "WHERE dv.id_venta = ?";

    private static final String SELECT_TOTAL_CLIENT_LAST_SALE = "SELECT SUM(dv.cantidad * p.precio_unidad) AS total "
            + "FROM ventas.venta v"
            + "JOIN ventas.detalle_venta dv ON v.id = dv.id_venta "
            + "JOIN ventas.producto p ON dv.id_producto = p.id "
            + "WHERE v.nit_cliente = ?";

    private final Connection connection;
    private final EmployeeDAO employeeDAO;
    private final SaleDAO saleDAO;

    public SaleRepository(Connection connection) {
        this.connection = connection;
        this.employeeDAO = new EmployeeDAO(connection);
        this.saleDAO = new SaleDAO(connection);
    }

    private double getLastClientSaleTotal(String clientNit) throws SQLException {
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

    private float getSaleDiscount(String clientNit) throws SQLException {
        double lastClientSaleTotal = getLastClientSaleTotal(clientNit);

        if (lastClientSaleTotal < 1000) {
            return 0;
        } else if (lastClientSaleTotal < 5000) {
            return 0.02f;
        } else if (lastClientSaleTotal < 10000) {
            return 0.05f;
        } else {
            return 0.1f;
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

                List<InvoiceLineItem> lineItems = new ArrayList<>();
                try (PreparedStatement lineItemsStatement = connection.prepareStatement(SELECT_SALE_DETAILS)) {
                    lineItemsStatement.setInt(1, id);

                    try (ResultSet lineItemsResultSet = lineItemsStatement.executeQuery()) {
                        while (lineItemsResultSet.next()) {
                            String productName = lineItemsResultSet.getString("product_name");
                            int amount = lineItemsResultSet.getInt("amount");
                            double unitPrice = lineItemsResultSet.getDouble("unit_price");
                            double total = lineItemsResultSet.getDouble("total");

                            lineItems.add(new InvoiceLineItem(productName, amount, unitPrice, total));
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
        String employeeCui = sale.employeeCui();
        Employee employee = employeeDAO.findEmployee(employeeCui);
        float discount = getSaleDiscount(sale.clientNit());

        if (employee.jobTitle() != JobTitle.VENDEDOR) {
            throw new IllegalArgumentException("Only salesman can make a sale");
        }

        Sale saleWithBranchId = new Sale(sale.id(),
                employeeCui,
                sale.clientNit(),
                employee.branchId(),
                LocalDate.now(),
                discount);

        saleDAO.createSale(saleWithBranchId);
    }

    public void updateSale(Sale sale) throws SQLException {
        saleDAO.updateSale(sale);
    }
}
