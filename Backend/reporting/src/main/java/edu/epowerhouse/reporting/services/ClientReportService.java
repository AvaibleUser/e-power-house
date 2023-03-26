package edu.epowerhouse.reporting.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.epowerhouse.common.models.aggregations.ClientWithPurchases;
import edu.epowerhouse.reporting.repositories.ClientReportRepository;

@Service
public class ClientReportService {
    private final ClientReportRepository clientReportRepository;

    public ClientReportService(ClientReportRepository clientReportRepository) {
        this.clientReportRepository = clientReportRepository;
    }

    public List<ClientWithPurchases> getClientsGeneratedHighestIncome() throws SQLException {
        return clientReportRepository.getClientsGeneratedHighestIncome();
    }
}
