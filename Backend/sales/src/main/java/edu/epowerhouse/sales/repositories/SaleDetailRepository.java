package edu.epowerhouse.sales.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import edu.epowerhouse.common.models.aggregations.InvoiceLineItem;
import edu.epowerhouse.common.models.records.SaleDetail;
import edu.epowerhouse.common.models.records.Stock;
import edu.epowerhouse.common.utils.DatabaseConnection;
import reactor.core.publisher.Mono;

@Repository
public class SaleDetailRepository {
    private static final String SELECT_SALE_DETAILS = "SELECT p.nombre AS product_name, dv.cantidad AS amount, "
            + "p.precio_unidad AS unit_price, (dv.cantidad * p.precio_unidad) AS total "
            + "FROM ventas.detalle_venta dv "
            + "JOIN ventas.producto p ON dv.id_producto = p.id "
            + "WHERE dv.id_venta = ?";

    private static final String INSERT_SALE_DETAIL = "INSERT INTO ventas.detalle_venta (id_venta, id_producto, cantidad) "
            + "VALUES (?, ?, ?)";

    private final Connection connection;
    private final WebClient webClient;

    public SaleDetailRepository(WebClient.Builder webClientBuilder) {
        this.connection = DatabaseConnection.getConnection();
        this.webClient = webClientBuilder.baseUrl("http://inventory:8080").build();
    }

    public List<InvoiceLineItem> findSaleDetailsBySaleId(int saleId) throws SQLException {
        List<InvoiceLineItem> lineItems = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_SALE_DETAILS)) {
            statement.setInt(1, saleId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String productName = resultSet.getString("product_name");
                    int amount = resultSet.getInt("amount");
                    double unitPrice = resultSet.getDouble("unit_price");
                    double total = resultSet.getDouble("total");

                    lineItems.add(new InvoiceLineItem(productName, amount, unitPrice, total));
                }
            }
        }
        return lineItems;
    }

    public void createSaleDetails(long saleId, int branchId, List<SaleDetail> saleDetails) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_SALE_DETAIL)) {
            for (SaleDetail saleDetail : saleDetails) {
                Stock stockPurchased = new Stock(
                        branchId,
                        saleDetail.productId(),
                        saleDetail.amount());

                statement.setLong(1, saleId);
                statement.setInt(2, saleDetail.productId());
                statement.setInt(3, saleDetail.amount());
                statement.addBatch();

                webClient.put().uri("/grocer/stocks/shipment/sale/")
                        .body(Mono.just(stockPurchased), Stock.class)
                        .retrieve()
                        .onStatus(
                                httpStatus -> httpStatus.value() != 200,
                                error -> Mono.error(new IllegalArgumentException("The stock cannot be purchased")))
                        .bodyToMono(Void.class)
                        .block();
            }
            statement.executeBatch();
        }
    }
}
