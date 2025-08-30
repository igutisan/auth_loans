package co.com.pragma.api.dto;

public record EditUserDto(
        String names,
        String lastNames,
        String dob,
        String phone,
        String address,
        String email,
        String salary
) {
}
