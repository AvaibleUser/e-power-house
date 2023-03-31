package edu.epowerhouse.common.models.aggregations;

public record ProductSold(
        int id,
        String name,
        String description,
        int amount,
        float totalRevenue,
        float totalIncome,
        float totalDiscounted) {
}
