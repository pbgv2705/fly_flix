package fly.be.flyflix.conteudo.dto.curso;

import fly.be.flyflix.conteudo.dto.modulo.DetalhamentoModulo;
import fly.be.flyflix.conteudo.entity.Curso;
import fly.be.flyflix.conteudo.entity.CursoModulo;

import java.util.Comparator;
import java.util.List;

public record DetalhamentoCurso(
        Long id,
        String titulo,
        String descricao,
        String imagemCapa,
        List<DetalhamentoModulo> modulos
) {
    public DetalhamentoCurso(Curso curso) {
        this(
                curso.getId(),
                curso.getTitulo(),
                curso.getDescricao(),
                curso.getImagemCapa(),
                curso.getCursoModulos().stream()
                        .sorted(Comparator.comparing(CursoModulo::getOrdem)) // ordena pela ordem no curso
                        .map(cm -> new DetalhamentoModulo(cm.getModulo()))
                        .toList()
        );
    }
}


