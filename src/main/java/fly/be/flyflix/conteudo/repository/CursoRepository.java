package fly.be.flyflix.conteudo.repository;

import fly.be.flyflix.conteudo.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    // Cursos recomendados: o usuário ainda não assistiu e possuem boa avaliação
    @Query("""
        SELECT c FROM Curso c
        WHERE :usuarioId NOT IN elements(c.usuariosQueAssistiramIds)
        AND c.mediaAvaliacoes >= 4.0
        ORDER BY c.mediaAvaliacoes DESC
    """)
    List<Curso> buscarRecomendados(@Param("usuarioId") Long usuarioId);

    // Últimos cursos publicados
    List<Curso> findTop10ByOrderByDataPublicacaoDesc();

    // Cursos mais populares por visualizações
    List<Curso> findTop10ByOrderByVisualizacoesDesc();
}


