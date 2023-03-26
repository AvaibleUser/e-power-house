package edu.epowerhouse.common.models.aggregations;

public record ClientWithPurchases(
    String nit,
    String name,
    int purchasesAmount,
    float totalRevenue,
    float totalDiscounted) {
}
