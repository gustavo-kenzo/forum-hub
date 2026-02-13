package br.com.gustavo.forum_hub.infra.security;

import br.com.gustavo.forum_hub.domain.usuario.UsuarioRepository;
import br.com.gustavo.forum_hub.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private UsuarioRepository usuarioRepository;
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = recuperarToken(request);

        if (token != null) {
            var userId = tokenService.getUserId(token);
            var usuario = usuarioRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }


    private String recuperarToken(HttpServletRequest request) {
        return Optional
                .ofNullable(request.getHeader("Authorization"))
                .map(t -> t.substring(7))
                .orElse(null);
    }
}
