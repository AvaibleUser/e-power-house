package edu.epowerhouse.common.models.aggregations;

import java.time.LocalDate;
import java.util.List;

public record Invoice(
        int id,
        LocalDate date,
        double discount,
        double total,
        String employeeName,
        String branchName,
        String branchAddress,
        String customerNit,
        String customerName,
        List<InvoiceLineItem> lineItems) {
}
