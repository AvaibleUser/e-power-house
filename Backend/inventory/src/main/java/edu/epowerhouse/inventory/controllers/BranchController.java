package edu.epowerhouse.inventory.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.epowerhouse.common.models.records.Branch;
import edu.epowerhouse.inventory.daos.BranchDAO;

@RestController
@RequestMapping("/grocer/branch")
public class BranchController {
    private final BranchDAO branchDao;
    
    public BranchController(BranchDAO branchDao) {
        this.branchDao = branchDao;
    }
    
    @GetMapping("/")
    public ResponseEntity<List<Branch>> getAllBranches() {
        try {
            List<Branch> branches = branchDao.findAllBranches();
            return ResponseEntity.ok(branches);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
