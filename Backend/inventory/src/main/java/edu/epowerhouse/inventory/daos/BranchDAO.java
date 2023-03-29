package edu.epowerhouse.inventory.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import edu.epowerhouse.common.models.records.Branch;
import edu.epowerhouse.common.utils.DatabaseConnection;

@Component
public class BranchDAO {
    private static final String INSERT_BRANCH_SQL = "INSERT INTO ventas.sucursal (nombre, direccion, telefono) VALUES (?, ?, ?)";
    private static final String FIND_BRANCH_SQL = "SELECT * FROM ventas.sucursal WHERE id = ?";
    private static final String UPDATE_BRANCH_SQL = "UPDATE ventas.sucursal SET nombre = ?, direccion = ?, telefono = ? WHERE id = ?";
    private static final String FIND_ALL_BRANCHES_SQL = "SELECT * FROM ventas.sucursal";

    private final Connection connection;

    public BranchDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void createBranch(Branch branch) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_BRANCH_SQL,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, branch.name());
            statement.setString(2, branch.address());
            statement.setString(3, branch.phone());

            statement.executeUpdate();
        }
    }

    public Branch findBranch(int id) throws SQLException {
        Branch branch = null;

        try (PreparedStatement statement = connection.prepareStatement(FIND_BRANCH_SQL)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }
                branch = new Branch(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("direccion"),
                        resultSet.getString("telefono"));
            }
        }
        return branch;
    }

    public void updateBranch(Branch branch) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_BRANCH_SQL)) {
            statement.setString(1, branch.name());
            statement.setString(2, branch.address());
            statement.setString(3, branch.phone());
            statement.setInt(4, branch.id());

            statement.executeUpdate();
        }
    }

    public List<Branch> findAllBranches() throws SQLException {
        List<Branch> branches = new ArrayList<>();

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(FIND_ALL_BRANCHES_SQL)) {
            while (resultSet.next()) {
                Branch branch = new Branch(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("direccion"),
                        resultSet.getString("telefono"));
                branches.add(branch);
            }
        }
        return branches;
    }
}
