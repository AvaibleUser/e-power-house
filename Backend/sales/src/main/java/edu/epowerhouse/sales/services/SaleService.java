package edu.epowerhouse.sales.services;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

import edu.epowerhouse.common.models.aggregations.Invoice;
import edu.epowerhouse.common.models.records.Sale;
import edu.epowerhouse.sales.repositories.SaleRepository;

@Service
public class SaleService {
    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public Invoice findSaleById(int saleId) throws SQLException {
        return saleRepository.findSaleById(saleId);
    }

    public void createSale(Sale sale) throws SQLException {
        saleRepository.createSale(sale);
    }

    public void updateSale(Sale sale) throws SQLException {
        saleRepository.updateSale(sale);
    }
}
