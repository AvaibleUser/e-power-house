package edu.epowerhouse.common.models.records;

import java.time.LocalDate;
import java.util.List;

public record Sale(
        int id,
        String employeeCui,
        String clientNit,
        int branchId,
        LocalDate date,
        float discount,
        List<SaleDetail> saleDetails) {
}
