package co.com.pragma.api.dto;

import co.com.pragma.model.user.UserRoles;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserResponseDto(
        String id,
        String names,
        String lastNames,
        String dni,
        LocalDate dob,
        String phone,
        String address,
        String email,
        BigDecimal salary,
        UserRoles role) {
}
