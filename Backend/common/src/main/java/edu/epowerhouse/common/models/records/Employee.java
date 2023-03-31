package edu.epowerhouse.common.models.records;

import java.time.LocalDate;

import edu.epowerhouse.common.models.enums.JobTitle;

public record Employee(
        String cui,
        String name,
        String lastName,
        String password,
        int branchId,
        int warehouseId,
        JobTitle jobTitle,
        LocalDate birthdate,
        String address,
        String email,
        String phone) {
}
