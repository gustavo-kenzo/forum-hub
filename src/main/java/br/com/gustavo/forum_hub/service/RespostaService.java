package br.com.gustavo.forum_hub.service;

import br.com.gustavo.forum_hub.domain.resposta.DadosCadastrarResposta;
import br.com.gustavo.forum_hub.domain.resposta.Resposta;
import br.com.gustavo.forum_hub.domain.resposta.RespostaRepository;
import br.com.gustavo.forum_hub.domain.topico.TopicoRepository;
import br.com.gustavo.forum_hub.domain.usuario.UsuarioRepository;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RespostaService {

    private UsuarioRepository usuarioRepository;
    private TopicoRepository topicoRepository;
    private RespostaRepository respostaRepository;

    public Resposta cadastrar(DadosCadastrarResposta dados) {
        var mensagem = dados.mensagem();
        var autor = usuarioRepository.getReferenceById(dados.autorId());
        var topico = topicoRepository.getReferenceById(dados.topicoId());
        var resposta = new Resposta(null, mensagem, LocalDateTime.now(), false, topico, autor);

        return respostaRepository.save(resposta);
    }

    public void definirSolucao(Long respostaId, Long usuarioId, Boolean ehSolucao) {
        var resposta = respostaRepository
                .findById(respostaId)
                .orElseThrow(() -> new ValidationException("Resposta não encontrada"));
        var topico = resposta.getTopico();


        if (!topico.getAutor().getId().equals(usuarioId))
            throw new ValidationException("Apenas o autor do tópico pode marcar resposta como solucao");

        //Desmarca qualquer solucao existente para evitar solucoes duplicadas
        if (ehSolucao)
            respostaRepository.desmacarSolucoesDoTopico(topico.getId());

        resposta.setSolucao(ehSolucao);
        boolean existeSolucao = respostaRepository.existsByTopicoIdAndSolucaoTrue(topico.getId());
        var topicoResolvido = !existeSolucao;
        topico.setStatus(topicoResolvido);
    }
}
