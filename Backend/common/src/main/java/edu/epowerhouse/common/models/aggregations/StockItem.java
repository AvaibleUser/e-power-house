package edu.epowerhouse.common.models.aggregations;

public record StockItem(
        int id,
        String branchName,
        String productName,
        double unitPrice,
        double unitCost,
        int amount,
        String description) {
}
