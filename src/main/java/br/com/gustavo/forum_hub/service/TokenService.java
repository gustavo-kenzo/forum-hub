package br.com.gustavo.forum_hub.service;

import br.com.gustavo.forum_hub.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    @Value("${api.forum.hub.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("Forum Hub")
                    .withSubject(usuario.getEmail())
                    .withClaim("nickname", usuario.getNome())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(dataExpiracao())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT");
        }
    }

    private Instant dataExpiracao() {
        return Instant.now().plus(2, ChronoUnit.HOURS);
    }

    public Long getUserId(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    // specify any specific claim validations
                    .withIssuer("Forum Hub")
                    // reusable verifier instance
                    .build()
                    .verify(token)
                    .getClaim("id")
                    .asLong();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token inválido ou expirado");
        }
    }
}
