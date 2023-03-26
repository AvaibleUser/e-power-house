package edu.epowerhouse.common.models.aggregations;

public record EmployeeSales(
    String cui,
    String employeeName,
    String branchName,
    int salesAmount,
    float totalRevenue,
    float totalDiscounted) {
}
