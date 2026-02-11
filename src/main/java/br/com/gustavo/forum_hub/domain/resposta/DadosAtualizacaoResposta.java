package br.com.gustavo.forum_hub.domain.resposta;

import jakarta.validation.constraints.Pattern;

public record DadosAtualizacaoResposta(
        @Pattern(regexp = "^(?!\\s*$).+", message = "Mensagem inválida")
        String mensagem
) {
}
