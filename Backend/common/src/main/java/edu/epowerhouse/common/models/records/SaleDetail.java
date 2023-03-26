package edu.epowerhouse.common.models.records;

public record SaleDetail(
        int saleId,
        int productId,
        int amount,
        float unitPrice) {
}
