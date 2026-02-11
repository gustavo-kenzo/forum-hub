package br.com.gustavo.forum_hub.service;

import br.com.gustavo.forum_hub.domain.curso.CursoRepository;
import br.com.gustavo.forum_hub.domain.resposta.DadosDetalhamentoResposta;
import br.com.gustavo.forum_hub.domain.resposta.RespostaRepository;
import br.com.gustavo.forum_hub.domain.topico.*;
import br.com.gustavo.forum_hub.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TopicoService {

    private TopicoRepository topicoRepository;
    private UsuarioRepository usuarioRepository;
    private CursoRepository cursoRepository;
    private RespostaRepository respostaRepository;

    public Topico cadastrar(DadosCadastrarTopico dados) {
        var titulo = dados.titulo();
        var mensagem = dados.mensagem();

        if (topicoRepository.existsByTituloAndMensagemIgnoreCase(titulo, mensagem))
            throw new ValidationException("Tópico já existe. Não pôde criar novamente");

        var autor = usuarioRepository.getReferenceById(dados.autorId());
        var curso = cursoRepository.getReferenceById(dados.cursoId());
        var topico = new Topico(null, titulo, mensagem, LocalDateTime.now(), true, autor, curso, null);
        return topicoRepository.save(topico);
    }

    public Page<DadosDetalhamentoTopico> listar(Long cursoId, Pageable pageable) {
        var topicos = topicoRepository.findAll(cursoId, pageable);
        return topicos.map(DadosDetalhamentoTopico::new);
    }

    public DadosDetalhamentoTopicoResposta listarTopico(Long topicoId) {
        var topico = topicoRepository.findById(topicoId).orElseThrow(() -> new ValidationException("Esse tópico não existe"));
        var respostas = respostaRepository.findByTopicoIdOrderByDataCriacao(topicoId).stream().map(DadosDetalhamentoResposta::new).toList();
        return new DadosDetalhamentoTopicoResposta(topico, respostas);
    }

    public DadosDetalhamentoTopico atualizarTopico(Long topicoId, Long autorId, @Valid DadosAtualizacaoTopico dados) {
        var topico = topicoRepository.findById(topicoId).orElseThrow(() -> new ValidationException("Esse topico não existe"));
        if (!topico.getAutor().getId().equals(autorId))
            throw new ValidationException("Somente o autor do tópico pode alterar o tópico");

        if (topicoRepository.existsByTituloAndMensagemIgnoreCase(dados.titulo(), dados.mensagem()))
            throw new ValidationException("Tópico duplicado! Não pode repetir titulo e mensagem");

        topico.atualizarTopico(dados);
        return new DadosDetalhamentoTopico(topico);
    }
}
