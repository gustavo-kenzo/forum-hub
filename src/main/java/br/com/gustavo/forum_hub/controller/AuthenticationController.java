package br.com.gustavo.forum_hub.controller;

import br.com.gustavo.forum_hub.domain.usuario.DadosAuthentication;
import br.com.gustavo.forum_hub.domain.usuario.Usuario;
import br.com.gustavo.forum_hub.infra.security.DadosTokenJWT;
import br.com.gustavo.forum_hub.service.TokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class AuthenticationController {

    private AuthenticationManager manager;

    private TokenService tokenService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid DadosAuthentication dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
