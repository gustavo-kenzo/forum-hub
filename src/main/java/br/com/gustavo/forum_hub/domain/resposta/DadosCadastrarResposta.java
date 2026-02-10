package br.com.gustavo.forum_hub.domain.resposta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastrarResposta(
        @NotBlank
        String mensagem,
        @NotNull
        Long topicoId,
        @NotNull
        Long autorId
) {
}
