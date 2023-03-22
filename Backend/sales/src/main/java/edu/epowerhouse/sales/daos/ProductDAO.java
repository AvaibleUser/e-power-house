package edu.epowerhouse.sales.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.epowerhouse.common.models.records.Product;

public class ProductDAO {
    private static final String INSERT_PRODUCT_SQL = "INSERT INTO ventas.producto (nombre, descripcion, precio_unidad, costo_unidad) VALUES (?, ?, ?, ?)";
    private static final String FIND_PRODUCT_SQL = "SELECT * FROM ventas.producto WHERE id = ?";
    private static final String UPDATE_PRODUCT_SQL = "UPDATE ventas.producto SET nombre = ?, descripcion = ?, precio_unidad = ?, costo_unidad = ? WHERE id = ?";
    private static final String DELETE_PRODUCT_SQL = "DELETE FROM ventas.producto WHERE id = ?";
    private static final String FIND_ALL_BRPRODUCT_SQL = "SELECT * FROM ventas.producto";

    private final Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    public void createProduct(Product product) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {
            statement.setString(1, product.name());
            statement.setString(2, product.description());
            statement.setFloat(3, product.unitPrice());
            statement.setFloat(4, product.unitCost());

            statement.executeUpdate();
        }
    }

    public Product findProduct(int id) throws SQLException {
        Product product = null;

        try (PreparedStatement statement = connection.prepareStatement(FIND_PRODUCT_SQL)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }
                product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion"),
                        resultSet.getFloat("precio_unidad"),
                        resultSet.getFloat("costo_unidad"));
            }
        }
        return product;
    }

    public void updateProduct(Product product) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT_SQL)) {
            statement.setString(1, product.name());
            statement.setString(2, product.description());
            statement.setFloat(3, product.unitPrice());
            statement.setFloat(4, product.unitCost());
            statement.setInt(5, product.id());

            statement.executeUpdate();
        }
    }

    public void deleteProduct(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public List<Product> findAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(FIND_ALL_BRPRODUCT_SQL)) {
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion"),
                        resultSet.getFloat("precio_unidad"),
                        resultSet.getFloat("costo_unidad"));
                products.add(product);
            }
        }
        return products;
    }
}
