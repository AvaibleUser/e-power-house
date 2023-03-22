package edu.epowerhouse.common.models.records;

import java.time.LocalDate;

public record Client(
        String nit,
        String name,
        String lastName,
        String address,
        String phone,
        LocalDate birthdate) {
}
