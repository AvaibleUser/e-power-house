package edu.epowerhouse.reporting.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import edu.epowerhouse.common.models.aggregations.ClientWithPurchases;

@Repository
public class ClientReportRepository {
    private static final String FIND_CLIENT_PURCHASE = "SELECT c.nit, CONCAT(c.nombre, ' ', c.apellido) AS name, SUM(dv.cantidad) AS sales_amount, "
    + "SUM(dv.cantidad * dv.precio_unidad * (1 - v.descuento)) AS total_revenue, SUM(dv.cantidad * dv.precio_unidad * v.descuento) AS total_discounted FROM ventas.venta v "
    + "JOIN ventas.detalle_venta dv ON dv.id_venta = v.id "
    + "JOIN ventas.cliente c ON c.nit = v.nit_cliente "
    + "GROUP BY c.nit, c.nombre, c.apellido ";

    private static final String CLIENTS_GENERATED_HIGHEST_INCOME = FIND_CLIENT_PURCHASE
            + "ORDER BY total_revenue DESC LIMIT 10";

    private final Connection connection;

    public ClientReportRepository(Connection connection) {
        this.connection = connection;
    }

    private List<ClientWithPurchases> getClientsWithCustomQuery(String query) throws SQLException {
        List<ClientWithPurchases> clientsWithPurchases = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String cui = resultSet.getString("cui");
                String name = resultSet.getString("name");
                int purchasesAmount = resultSet.getInt("sales_amount");
                float totalRevenue = resultSet.getFloat("total_revenue");
                float discounted = resultSet.getFloat("discounted");

                clientsWithPurchases.add(new ClientWithPurchases(
                        cui,
                        name,
                        purchasesAmount,
                        totalRevenue,
                        discounted));
            }
        }
        return clientsWithPurchases;
    }

    public List<ClientWithPurchases> getClientsGeneratedHighestIncome() throws SQLException {
        return getClientsWithCustomQuery(CLIENTS_GENERATED_HIGHEST_INCOME);
    }
}
