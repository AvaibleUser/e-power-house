package edu.epowerhouse.common.models.aggregations;

public record StockItem(
        String branchName,
        String productName,
        double unitPrice,
        double unitCost,
        int quantity,
        String description) {
}
