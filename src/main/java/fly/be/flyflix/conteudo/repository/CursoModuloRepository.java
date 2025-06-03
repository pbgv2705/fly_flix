package fly.be.flyflix.conteudo.repository;

import fly.be.flyflix.conteudo.entity.Curso;
import fly.be.flyflix.conteudo.entity.CursoModulo;
import fly.be.flyflix.conteudo.entity.CursoModuloId;
import fly.be.flyflix.conteudo.entity.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
import java.util.List;

@Repository
public interface CursoModuloRepository extends JpaRepository<CursoModulo, CursoModuloId> {
    List<CursoModulo> findByCurso_IdOrderByOrdemAsc(Long cursoId);
    boolean existsByCursoAndModulo(Curso curso, Modulo modulo);
    @Query("SELECT MAX(cm.ordem) FROM CursoModulo cm WHERE cm.curso = :curso")
    Optional<Integer> findMaxOrdemByCurso(Curso curso);

}
