package com.pds.pingou.security.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @Email(message = "O formato do email é inválido.")
        @NotBlank(message = "O email não pode estar em branco.")
        @Schema(example = "jorgesilva@mail.com")
        String email,

        @NotBlank(message = "A senha não pode estar em branco.")
        @Schema(example = "12345678")
        String password
) {
}
