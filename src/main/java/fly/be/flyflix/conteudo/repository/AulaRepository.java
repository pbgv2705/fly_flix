package fly.be.flyflix.conteudo.repository;

import fly.be.flyflix.conteudo.entity.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {

    List<Aula> findByModuloId(Long moduloId);
    @Query("SELECT a FROM Aula a WHERE a.modulo.curso.id = :cursoId")
    List<Aula> findByCursoId(@Param("cursoId") Long cursoId);
}
