package com.pds.pingou.security.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotBlank(message = "O nome não pode estar em branco.")
        @Schema(example = "Jorge")
        String nome,

        @NotBlank(message = "O sobrenome não pode estar em branco.")
        @Schema(example = "Silva")
        String sobrenome,

        @Email(message = "O formato do email é inválido.")
        @NotBlank(message = "O email não pode estar em branco.")
        @Schema(example = "jorgesilva@mail.com")
        String email,

        @NotBlank(message = "A senha não pode estar em branco.")
        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
        @Schema(example = "12345678")
        String password
) {
}
