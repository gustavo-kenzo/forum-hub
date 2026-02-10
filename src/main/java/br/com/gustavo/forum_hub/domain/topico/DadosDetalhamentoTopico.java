package br.com.gustavo.forum_hub.domain.topico;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        String autor,
        String curso,
        Boolean status,
        LocalDateTime dataCriacao) {

    public DadosDetalhamentoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getAutor().getNome(),
                topico.getCurso().getNome(),
                topico.getStatus(),
                topico.getDataCriacao()
        );
    }
}
