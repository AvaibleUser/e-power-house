package edu.epowerhouse.common.models.aggregations;

public record InvoiceLineItem(
        String productName,
        int quantity,
        double unitPrice,
        double total) {
}
