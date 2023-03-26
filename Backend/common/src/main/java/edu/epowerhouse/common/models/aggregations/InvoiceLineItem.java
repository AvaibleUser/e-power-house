package edu.epowerhouse.common.models.aggregations;

public record InvoiceLineItem(
        String productName,
        int amount,
        double unitPrice,
        double total) {
}
