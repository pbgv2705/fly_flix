package fly.be.flyflix.conteudo.repository;

import fly.be.flyflix.conteudo.entity.ResultadoQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResultadoQuizRepository extends JpaRepository<ResultadoQuiz, Long> {
    Optional<ResultadoQuiz> findByAlunoIdAndCursoId(Long alunoId, Long cursoId);
}

