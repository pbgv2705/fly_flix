package fly.be.flyflix.conteudo.dto.curso;

import fly.be.flyflix.conteudo.dto.modulo.DetalhamentoModulo;
import fly.be.flyflix.conteudo.entity.Curso;

import java.util.List;

public record DetalhamentoCurso(
        Long id,
        String titulo,
        String descricao,
        String imagemCapa,
        List<String> tags,
        String nivel,
        Long autorId,
        List<DetalhamentoModulo> modulos // ✅ Inclusão dos módulos com aulas
) {
    public DetalhamentoCurso(Curso curso) {
        this(
                curso.getId(),
                curso.getTitulo(),
                curso.getDescricao(),
                curso.getImagemCapa(),
                curso.getTags(),
                String.valueOf(curso.getNivel()),
                curso.getAutorId(),
                curso.getModulos().stream()
                        .map(DetalhamentoModulo::new)
                        .toList()
        );
    }
}
