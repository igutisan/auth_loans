package co.com.pragma.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record LoginDto(
        @NotBlank(message = "El email no puede estar vacío.")
        @Email(message = "El formato del email no es válido.")
        String email,

        @NotBlank(message = "El password no puede estar vacío.")
        String password) {
}
