package br.com.gustavo.forum_hub.domain.topico;

public record DadosDetalhamentoTopico(
        String titulo,
        String mensagem,
        String autor,
        String curso,
        Boolean status) {

    public DadosDetalhamentoTopico(Topico topico) {
        this(
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getAutor().getNome(),
                topico.getCurso().getNome(),
                topico.getStatus()
        );
    }
}
