package edu.epowerhouse.common.models.aggregations;

public record InventoryItem(
        int id,
        String warehouseName,
        String productName,
        double unitPrice,
        double unitCost,
        int amount,
        String description) {
}
