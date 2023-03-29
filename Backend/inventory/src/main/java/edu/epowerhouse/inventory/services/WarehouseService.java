package edu.epowerhouse.inventory.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.epowerhouse.common.models.records.Warehouse;
import edu.epowerhouse.inventory.daos.WarehouseDAO;

@Service
public class WarehouseService {
    private final WarehouseDAO warehouseDAO;

    public WarehouseService(WarehouseDAO warehouseDao) {
        this.warehouseDAO = warehouseDao;
    }

    public List<Warehouse> findAllWarehousees() throws SQLException {
        return warehouseDAO.findAllWarehouses();
    }

    public void createWarehouse(Warehouse warehouse) throws SQLException {
        warehouseDAO.createWarehouse(warehouse);
    }

    public void updateWarehouse(Warehouse warehouse) throws SQLException {
        warehouseDAO.updateWarehouse(warehouse);
    }
}
