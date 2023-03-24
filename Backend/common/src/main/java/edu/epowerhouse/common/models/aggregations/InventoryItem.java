package edu.epowerhouse.common.models.aggregations;

public record InventoryItem(
        String warehouseName,
        String productName,
        double unitPrice,
        double unitCost,
        int quantity,
        String description) {
}
