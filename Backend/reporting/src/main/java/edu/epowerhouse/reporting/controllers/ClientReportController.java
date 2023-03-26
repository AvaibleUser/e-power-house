package edu.epowerhouse.reporting.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.epowerhouse.common.models.aggregations.ClientWithPurchases;
import edu.epowerhouse.reporting.services.ClientReportService;

@RestController
@RequestMapping("/client-reports")
public class ClientReportController {
    private final ClientReportService clientReportService;

    public ClientReportController(ClientReportService clientReportService) {
        this.clientReportService = clientReportService;
    }

    @GetMapping("/highest-income")
    public List<ClientWithPurchases> getClientsGeneratedHighestIncome() throws SQLException {
        return clientReportService.getClientsGeneratedHighestIncome();
    }
}
