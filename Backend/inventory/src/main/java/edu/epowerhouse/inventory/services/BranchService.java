package edu.epowerhouse.inventory.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.epowerhouse.common.models.records.Branch;
import edu.epowerhouse.inventory.daos.BranchDAO;

@Service
public class BranchService {
    private final BranchDAO branchDAO;

    public BranchService(BranchDAO branchDao) {
        this.branchDAO = branchDao;
    }

    public List<Branch> findAllBranches() throws SQLException {
        return branchDAO.findAllBranches();
    }

    public void createBranch(Branch branch) throws SQLException {
        branchDAO.createBranch(branch);
    }

    public void updateBranch(Branch branch) throws SQLException {
        branchDAO.updateBranch(branch);
    }
}
