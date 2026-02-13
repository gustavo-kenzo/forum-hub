package br.com.gustavo.forum_hub.service;

import br.com.gustavo.forum_hub.domain.resposta.*;
import br.com.gustavo.forum_hub.domain.topico.TopicoRepository;
import br.com.gustavo.forum_hub.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
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

        //Impede alternância indevida
        if (resposta.getSolucao().equals(ehSolucao))
            return;

        //Marca solucao
        if (ehSolucao) {
            //Se tem solucao topico fechado (false)
            respostaRepository.desmacarSolucoesRespostaDoTopico(topico.getId());
            resposta.setSolucao(true);
            topico.setStatus(false);
        }
        //Desmarca solucao
        else {
            //Se nao tem solucao topico aberto (true)
            resposta.setSolucao(false);
            topico.setStatus(true);
        }
    }

    public DadosDetalhamentoResposta atualizar(Long respostaId, Long autorId, @Valid DadosAtualizacaoResposta dados) {
        var resposta = respostaRepository.findById(respostaId).orElseThrow(() -> new ValidationException("Essa resposta não existe"));

        if (!resposta.getAutor().getId().equals(autorId))
            throw new ValidationException("Somente o autor da resposta pode alterar o conteúdo dessa resposta");

        resposta.atualizarResposta(dados);
        return new DadosDetalhamentoResposta(resposta);
    }

    public void deletarResposta(Long respostaId, Long usuarioId) {
        var resposta = respostaRepository.findById(respostaId).orElseThrow(() -> new ValidationException("Essa resposta não existe"));
        if (!resposta.getAutor().getId().equals(usuarioId))
            throw new ValidationException("Somente autor da resposta pode deletá-la");
        respostaRepository.deleteById(resposta.getId());
    }
}
