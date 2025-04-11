package fly.be.flyflix.conteudo.repository;

import fly.be.flyflix.conteudo.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    // Recomendação baseada em cursos que o usuário ainda não viu e com boas avaliações
    @Query("""
        SELECT c FROM Curso c
        WHERE c.id NOT IN (
            SELECT c2.id FROM Curso c2
            JOIN c2.usuariosQueAssistiram u
            WHERE u.id = :usuarioId
        )
        AND c.mediaAvaliacoes >= 4.0
        ORDER BY c.mediaAvaliacoes DESC
    """)
    List<Curso> buscarRecomendados(@Param("usuarioId") Long usuarioId);

    // Recomendação com base em comportamento de usuários similares
    @Query("""
        SELECT c FROM Curso c
        JOIN c.usuariosQueAssistiram u
        WHERE u.id IN (
            SELECT u2.id FROM Curso c2
            JOIN c2.usuariosQueAssistiram u2
            WHERE c2 IN (
                SELECT c3 FROM Curso c3
                JOIN c3.usuariosQueAssistiram u3
                WHERE u3.id = :usuarioId
            )
            AND u2.id <> :usuarioId
        )
        GROUP BY c
        ORDER BY COUNT(u) DESC
    """)
    List<Curso> findCursosRecomendados(@Param("usuarioId") Long usuarioId);

    // Novos cursos (últimos publicados)
    List<Curso> findTop10ByOrderByDataPublicacaoDesc();

    // Cursos mais populares (por visualizações)
    List<Curso> findTop10ByOrderByVisualizacoesDesc();
}

