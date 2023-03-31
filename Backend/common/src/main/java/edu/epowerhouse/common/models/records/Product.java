package edu.epowerhouse.common.models.records;

public record Product(
        int id,
        String name,
        String description,
        float unitPrice,
        float unitCost) {
}
