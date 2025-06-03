package fly.be.flyflix.conteudo.repository;

import fly.be.flyflix.conteudo.entity.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Long> {
    List<Modulo> findByCursos_Id(Long cursoId);
    List<Modulo> findByTituloContainingIgnoreCase(String titulo);


}

