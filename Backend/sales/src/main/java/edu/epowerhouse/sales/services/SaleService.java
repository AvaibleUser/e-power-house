package edu.epowerhouse.sales.services;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import edu.epowerhouse.common.models.aggregations.Invoice;
import edu.epowerhouse.common.models.aggregations.InvoiceLineItem;
import edu.epowerhouse.common.models.enums.JobTitle;
import edu.epowerhouse.common.models.records.Employee;
import edu.epowerhouse.common.models.records.Sale;
import edu.epowerhouse.sales.repositories.SaleDetailRepository;
import edu.epowerhouse.sales.repositories.SaleRepository;

@Service
public class SaleService {
    private final SaleRepository saleRepository;
    private final SaleDetailRepository saleDetailRepository;
    private final WebClient webClient;

    public SaleService(SaleRepository saleRepository, SaleDetailRepository saleDetailRepository,
            WebClient.Builder webClientBuilder) {
        this.saleRepository = saleRepository;
        this.saleDetailRepository = saleDetailRepository;
        this.webClient = webClientBuilder.baseUrl("http://authentication:8080").build();
    }

    private float getSaleDiscount(String clientNit) throws SQLException {
        double lastClientSaleTotal = saleRepository.getLastClientSaleTotal(clientNit);

        if (lastClientSaleTotal < 1000) {
            return 0;
        } else if (lastClientSaleTotal < 5000) {
            return 0.02f;
        } else if (lastClientSaleTotal < 10000) {
            return 0.05f;
        } else {
            return 0.1f;
        }
    }

    public Invoice findSaleById(int saleId) throws SQLException {
        Invoice invoice = saleRepository.findSaleById(saleId);
        List<InvoiceLineItem> lineItems = saleDetailRepository.findSaleDetailsBySaleId(saleId);

        double total = lineItems.stream().mapToDouble(InvoiceLineItem::total).sum();

        return new Invoice(
                invoice.id(),
                invoice.date(),
                invoice.discount(),
                total,
                invoice.employeeName(),
                invoice.branchName(),
                invoice.branchAddress(),
                invoice.customerNit(),
                invoice.customerName(),
                lineItems);
    }

    public float createSale(Sale sale) throws SQLException {
        String employeeCui = sale.employeeCui();
        Employee employee = webClient.get().uri("/public/employee/" + employeeCui)
                .retrieve()
                .bodyToMono(Employee.class)
                .block();

        float discount = 0;

        if (!sale.clientNit().strip().equals("CF")) {
            discount = getSaleDiscount(sale.clientNit());
        }

        if (employee.jobTitle() != JobTitle.VENDEDOR) {
            throw new IllegalArgumentException("Only salesman can make a sale");
        }

        Sale saleWithBranchId = new Sale(sale.id(),
                employeeCui,
                sale.clientNit(),
                employee.branchId(),
                LocalDate.now(),
                discount,
                sale.saleDetails());

        saleRepository.createSale(saleWithBranchId);

        return discount;
    }

    public void updateSale(Sale sale) throws SQLException {
        saleRepository.updateSale(sale);
    }
}
