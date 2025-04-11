package fly.be.flyflix.conteudo.repository;

import fly.be.flyflix.conteudo.entity.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Long> {
}

