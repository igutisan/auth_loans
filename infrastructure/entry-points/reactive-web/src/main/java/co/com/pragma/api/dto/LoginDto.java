package co.com.pragma.api.dto;

import lombok.Builder;

@Builder
public record LoginDto(String email, String password) {
}
