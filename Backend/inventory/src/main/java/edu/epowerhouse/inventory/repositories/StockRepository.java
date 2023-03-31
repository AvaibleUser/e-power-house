package edu.epowerhouse.inventory.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import edu.epowerhouse.common.models.aggregations.StockItem;
import edu.epowerhouse.common.models.records.Stock;
import edu.epowerhouse.common.utils.DatabaseConnection;
import edu.epowerhouse.inventory.daos.StockDAO;

@Repository
public class StockRepository {
    private static final String FIND_BRANCH_STOCK_SQL = "SELECT p.id, su.nombre AS branch_name, p.nombre AS product_name, "
            + "p.precio_unidad AS unit_price, p.costo_unidad AS unit_cost, st.cantidad, p.descripcion FROM ventas.stock st "
            + "JOIN ventas.sucursal su ON su.id = st.id_sucursal "
            + "JOIN ventas.producto p ON p.id = st.id_producto "
            + "WHERE st.id_sucursal = ?";

    private final Connection connection;
    private final StockDAO stockDAO;

    public StockRepository(StockDAO stockDAO) {
        this.connection = DatabaseConnection.getConnection();
        this.stockDAO = stockDAO;
    }

    public Stock findStock(int branchId, int productId) throws SQLException {
        return stockDAO.findStock(branchId, productId);
    }

    public List<StockItem> findCompleteStock(int branchId) throws SQLException {
        List<StockItem> stock = new ArrayList<>();

        try (PreparedStatement stockStatement = connection.prepareStatement(FIND_BRANCH_STOCK_SQL)) {
            stockStatement.setInt(1, branchId);

            try (ResultSet stockResultSet = stockStatement.executeQuery()) {
                while (stockResultSet.next()) {
                    int id = stockResultSet.getInt("id");
                    String branchName = stockResultSet.getString("branch_name");
                    String productName = stockResultSet.getString("product_name");
                    double unitPrice = stockResultSet.getDouble("unit_price");
                    double unitCost = stockResultSet.getDouble("unit_cost");
                    int amount = stockResultSet.getInt("cantidad");
                    String description = stockResultSet.getString("descripcion");

                    stock.add(new StockItem(id, branchName, productName, unitPrice, unitCost, amount, description));
                }
            }
        }

        return stock;
    }

    public void createStock(Stock stock) throws SQLException {
        stockDAO.createStock(stock);
    }

    public void updateStock(Stock stock) throws SQLException {
        stockDAO.updateStock(stock);
    }
}
