package edu.epowerhouse.common.models.aggregations;

public record BranchSales(
        int id,
        String name,
        String address,
        String phone,
        int salesAmount,
        float totalRevenue,
        float discounted) {
}
