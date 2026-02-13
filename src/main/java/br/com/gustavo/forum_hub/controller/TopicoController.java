package br.com.gustavo.forum_hub.controller;

import br.com.gustavo.forum_hub.domain.topico.DadosAtualizacaoTopico;
import br.com.gustavo.forum_hub.domain.topico.DadosCadastrarTopico;
import br.com.gustavo.forum_hub.domain.topico.DadosDetalhamentoTopico;
import br.com.gustavo.forum_hub.service.TopicoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
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
        var dto = new DadosDetalhamentoTopico(topico);
        System.out.println(dto);
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity listar(
            @RequestParam(required = false) Long cursoId,
            @PageableDefault(page = 0, size = 10, sort = {"dataCriacao"}, direction = Sort.Direction.DESC) Pageable pageable) {
        var dadosTopicos = topicoService.listar(cursoId, pageable);
        return ResponseEntity.ok(dadosTopicos);
    }

    @GetMapping("/{topicoId}")
    public ResponseEntity listarTopico(@PathVariable Long topicoId) {
        var dadoTopico = topicoService.listarTopico(topicoId);
        return ResponseEntity.ok(dadoTopico);
    }

    @PutMapping("/{topicoId}")
    @Transactional
    public ResponseEntity atualizarTopico(
            @PathVariable Long topicoId,
            @AuthenticationPrincipal(expression = "id") Long autorId,
            @RequestBody @Valid DadosAtualizacaoTopico dados
    ) {
        var dadosTopicoAtualizado = topicoService.atualizarTopico(topicoId, autorId, dados);
        return ResponseEntity.ok(dadosTopicoAtualizado);
    }

    @DeleteMapping("/{topicoId}")
    @Transactional
    public ResponseEntity deletarTopico(@PathVariable Long topicoId, @AuthenticationPrincipal(expression = "id") Long usuarioId) {
        topicoService.deletarTopico(topicoId, usuarioId);
        return ResponseEntity.noContent().build();
    }
}
