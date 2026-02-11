package br.com.gustavo.forum_hub.domain.resposta;

import br.com.gustavo.forum_hub.domain.topico.Topico;
import br.com.gustavo.forum_hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "respostas")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensagem;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Setter
    private Boolean solucao;

    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    public void atualizarResposta(DadosAtualizacaoResposta dados) {
        if (dados.mensagem() != null) this.mensagem = dados.mensagem();
    }

}
