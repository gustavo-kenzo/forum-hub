package br.com.gustavo.forum_hub.domain.topico;

import br.com.gustavo.forum_hub.domain.resposta.DadosDetalhamentoResposta;

import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalhamentoTopicoResposta(
        Long id,
        String titulo,
        String mensagem,
        String autor,
        String curso,
        Boolean status,
        LocalDateTime dataCriacao,
        List<DadosDetalhamentoResposta> respostas
) {
    public DadosDetalhamentoTopicoResposta(Topico topico, List<DadosDetalhamentoResposta> respostas) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getAutor().getNome(),
                topico.getCurso().getNome(),
                topico.getStatus(),
                topico.getDataCriacao(),
                respostas
        );
    }
}
