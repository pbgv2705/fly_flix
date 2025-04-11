package fly.be.flyflix.conteudo.repository;

import fly.be.flyflix.conteudo.entity.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {
}
