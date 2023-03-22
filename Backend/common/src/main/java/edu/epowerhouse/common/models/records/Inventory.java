package edu.epowerhouse.common.models.records;

public record Inventory(
        int warehouseId,
        int productId,
        int quantity) {
}
