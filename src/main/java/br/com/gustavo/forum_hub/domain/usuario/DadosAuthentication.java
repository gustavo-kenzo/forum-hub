package br.com.gustavo.forum_hub.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosAuthentication(
        @NotBlank
        String login,
        @NotBlank
        String senha
) {
}
