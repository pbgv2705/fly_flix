package fly.be.flyflix.conteudo.dto.modulo;

import fly.be.flyflix.conteudo.entity.CursoModulo;
import fly.be.flyflix.conteudo.entity.Modulo;

public record DetalhamentoModulo(
        Long id,
        String titulo,
        Integer ordem
) {
    // Construtor para uso direto com Modulo (sem ordem definida no curso)
    public DetalhamentoModulo(Modulo modulo) {
        this(modulo.getId(), modulo.getTitulo(), null);
    }

    // Construtor para uso quando o módulo está vinculado a um curso
    public DetalhamentoModulo(CursoModulo cursoModulo) {
        this(
                cursoModulo.getModulo().getId(),
                cursoModulo.getModulo().getTitulo(),
                cursoModulo.getOrdem()
        );
    }
}





