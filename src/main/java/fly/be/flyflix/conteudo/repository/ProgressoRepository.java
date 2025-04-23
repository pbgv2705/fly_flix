package fly.be.flyflix.conteudo.repository;

import fly.be.flyflix.conteudo.entity.ProgressoAluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgressoRepository extends JpaRepository<ProgressoAluno, Long> {
    List<ProgressoAluno> findByAlunoIdAndCursoId(Long alunoId, Long cursoId);

    Optional<ProgressoAluno> findByAlunoIdAndAulaId(Long alunoId, Long aulaId);
}

