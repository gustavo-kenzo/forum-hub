package br.com.gustavo.forum_hub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloAndMensagemIgnoreCase(String titulo, String mensagem);

    @Query("""
            select t from Topico t
            where (:cursoId is null or t.curso.id=:cursoId)
            """)
    Page<Topico> findAll(Long cursoId, Pageable pageable);
}
