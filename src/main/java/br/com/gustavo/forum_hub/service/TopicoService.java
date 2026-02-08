package br.com.gustavo.forum_hub.service;

import br.com.gustavo.forum_hub.domain.curso.CursoRepository;
import br.com.gustavo.forum_hub.domain.topico.DadosCadastrarTopico;
import br.com.gustavo.forum_hub.domain.topico.Topico;
import br.com.gustavo.forum_hub.domain.topico.TopicoRepository;
import br.com.gustavo.forum_hub.domain.usuario.UsuarioRepository;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TopicoService {

    private TopicoRepository topicoRepository;
    private UsuarioRepository usuarioRepository;
    private CursoRepository cursoRepository;

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
}
