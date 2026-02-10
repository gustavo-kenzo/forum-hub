package br.com.gustavo.forum_hub.domain.resposta;

import java.time.LocalDateTime;

public record DadosDetalhamentoResposta(
        Long id,
        String mensagem,
        String topicoName,
        String autorName,
        LocalDateTime dataCriacao,
        Boolean solucao
) {

    public DadosDetalhamentoResposta(Resposta resposta) {
        this(
                resposta.getId(),
                resposta.getMensagem(),
                resposta.getTopico().getTitulo(),
                resposta.getAutor().getNome(),
                resposta.getDataCriacao(),
                resposta.getSolucao()
        );
    }
}
