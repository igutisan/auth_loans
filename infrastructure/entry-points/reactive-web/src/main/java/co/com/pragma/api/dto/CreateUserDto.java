package co.com.pragma.api.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;


public record CreateUserDto(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
        @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre solo puede contener letras y espacios")
        String names,

        @NotBlank(message = "Los apellidos son obligatorios")
        @Size(min = 2, max = 50, message = "Los apellidos deben tener entre 2 y 50 caracteres")
        @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "Los apellidos solo pueden contener letras y espacios")
        String lastNames,

        @NotNull(message = "La fecha de nacimiento es obligatoria")
        @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
        LocalDate dob,

        @NotBlank(message = "La contraseña es obligatoria.")
        @Size(min = 8, max = 72, message = "La contraseña debe tener entre 12 y 72 caracteres.")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).+$",
                message = "Debe incluir mínimo: una letra minúscula, una mayúscula, un número y un símbolo."
        )
        @Pattern(
                regexp = "^\\S+$",
                message = "La contraseña no puede contener espacios."
        )
        String password,

        @NotBlank(message = "El teléfono es obligatorio")
        @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "El teléfono debe tener entre 10 y 15 dígitos")
        String phone,

        @NotBlank(message = "La dirección es obligatoria")
        @Size(min = 10, max = 200, message = "La dirección debe tener entre 10 y 200 caracteres")
        String address,

        @NotBlank(message = "El correo electrónico es obligatorio")
        @Email(message = "El formato del correo electrónico no es válido")
        @Size(max = 100, message = "El correo electrónico no puede exceder 100 caracteres")
        String email,

        @NotNull(message = "El DNI es obligatorio")
        @Size(min = 8, max = 12, message = "El DNI debe tener entre 8 y 12 caracteres")
        @Pattern(regexp = "\\d{8,12}", message = "El DNI debe contener solo números y tener entre 8 y 12 dígitos")
        String dni,

        @NotNull(message = "El salario base es obligatorio")
        @DecimalMin(value = "0.0", inclusive = true, message = "El salario base debe ser mayor o igual a 0")
        @DecimalMax(value = "15000000", inclusive = true, message = "El salario base excede el límite máximo")
        @Digits(integer = 8, fraction = 2, message = "El salario base debe tener máximo 8 dígitos enteros y 2 decimales")
        BigDecimal salary,

        @NotBlank(message = "El rol es obligatorio")
        @Pattern(regexp = "(?i)^(ADMIN|ASESOR|CLIENTE)$", message = "El rol no es válido. Debe ser ADMIN, ASESOR o CLIENTE.")
        String role
) {
}
