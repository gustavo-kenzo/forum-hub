package br.com.gustavo.forum_hub.controller;

import br.com.gustavo.forum_hub.domain.resposta.DadosCadastrarResposta;
import br.com.gustavo.forum_hub.domain.resposta.DadosDetalhamentoResposta;
import br.com.gustavo.forum_hub.service.RespostaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("resposta")
@AllArgsConstructor
public class RespostaController {

    private RespostaService respostaService;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarResposta(@RequestBody @Valid DadosCadastrarResposta dados, UriComponentsBuilder uriBuilder) {
        var resposta = respostaService.cadastrar(dados);
        var uri = uriBuilder.path("/resposta/{id}").buildAndExpand(resposta.getId()).toUri();

        var autor = resposta.getAutor().getNome();
        var topico = resposta.getTopico().getTitulo();

        var dto = new DadosDetalhamentoResposta(resposta);

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{respostaId}/solucao")
    @Transactional
    public ResponseEntity marcarComoSolucao(@PathVariable Long respostaId, @RequestParam Long usuarioId) {
        respostaService.definirSolucao(respostaId, usuarioId, true);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{respostaId}/solucao")
    @Transactional
    public ResponseEntity desmarcarComoSolucao(@PathVariable Long respostaId, @RequestParam Long usuarioId) {
        respostaService.definirSolucao(respostaId, usuarioId, false);
        return ResponseEntity.noContent().build();
    }

}
