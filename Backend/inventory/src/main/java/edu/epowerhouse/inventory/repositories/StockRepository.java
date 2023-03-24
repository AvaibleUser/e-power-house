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
import edu.epowerhouse.inventory.daos.StockDAO;

@Repository
public class StockRepository {
    private static final String FIND_BRANCH_STOCK_SQL = "SELECT su.nombre AS sucursal_name, p.nombre AS producto_nombre, p.precio_unidad, "
            + "p.costo_unidad, st.cantidad, p.descripcion FROM ventas.stock st JOIN ventas.sucursal su b ON su.id = st.id_bodega"
            + "JOIN ventas.producto p ON p.id = st.id_producto WHERE st.id_sucursal = ?;";

    private final Connection connection;
    private final StockDAO stockDAO;

    public StockRepository(Connection connection) {
        this.connection = connection;
        this.stockDAO = new StockDAO(connection);
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
                    String branchName = stockResultSet.getString("bodega_name");
                    String productName = stockResultSet.getString("producto_nombre");
                    double unitPrice = stockResultSet.getDouble("precio_unidad");
                    double unitCost = stockResultSet.getDouble("costo_unidad");
                    int quantity = stockResultSet.getInt("cantidad");
                    String description = stockResultSet.getString("descripcion");
    
                    stock.add(new StockItem(branchName, productName, unitPrice, unitCost, quantity, description));
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
