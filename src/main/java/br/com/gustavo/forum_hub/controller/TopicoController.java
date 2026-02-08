package br.com.gustavo.forum_hub.controller;

import br.com.gustavo.forum_hub.service.TopicoService;
import br.com.gustavo.forum_hub.domain.topico.DadosCadastrarTopico;
import br.com.gustavo.forum_hub.domain.topico.DadosDetalhamentoTopico;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("topico")
@AllArgsConstructor
public class TopicoController {

    private TopicoService topicoService;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastrarTopico dados, UriComponentsBuilder uriBuilder) {

        var topico = topicoService.cadastrar(dados);
        System.out.println(topico);
        var uri = uriBuilder.path("/topico/{id}").buildAndExpand(topico.getId()).toUri();
        var dto = new DadosDetalhamentoTopico(topico.getTitulo(), topico.getMensagem(), topico.getAutor().getNome(), topico.getCurso().getNome(), topico.getStatus());
        System.out.println(dto);
        return ResponseEntity.created(uri).body(dto);
    }
}
