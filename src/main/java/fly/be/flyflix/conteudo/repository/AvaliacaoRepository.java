package fly.be.flyflix.conteudo.repository;

import fly.be.flyflix.conteudo.entity.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    List<Avaliacao> findByCursoId(Long cursoId);
}
