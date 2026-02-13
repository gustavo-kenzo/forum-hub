package br.com.gustavo.forum_hub.domain.resposta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {
    @Modifying
    @Query("""
            update Resposta r
            set r.solucao = false
            where r.topico.id = :topicoId
            """)
    void desmacarSolucoesRespostaDoTopico(Long topicoId);

    boolean existsByTopicoIdAndSolucaoTrue(Long id);


    List<Resposta> findByTopicoIdOrderByDataCriacao(Long topicoId);
}
