package br.com.gustavo.forum_hub.domain.topico;

import jakarta.validation.constraints.Pattern;

public record DadosAtualizacaoTopico(
        @Pattern(regexp = "^(?!\\s*$).+", message = "Título inválido")
        String titulo,

        @Pattern(regexp = "^(?!\\s*$).+", message = "Mensagem inválida")
        String mensagem
) {
}
