package br.com.gustavo.forum_hub.domain.resposta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosDetalhamentoResposta(
        @NotBlank
        String mensagem,

        @NotBlank
        String topicoName,

        @NotBlank
        String autorName,

        @NotNull
        LocalDateTime dataCriacao,

        @NotNull
        Boolean solucao
) {
}
