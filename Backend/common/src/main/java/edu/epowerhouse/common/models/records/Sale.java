package edu.epowerhouse.common.models.records;

import java.time.LocalDate;

public record Sale(
        int id,
        String employeeCui,
        String clientNit,
        int branchId,
        LocalDate date,
        float discount) {
}
