package fly.be.flyflix.conteudo.dto.modulo;

import fly.be.flyflix.conteudo.entity.CursoModulo;
import fly.be.flyflix.conteudo.entity.Modulo;

public record DetalhamentoModulo(Long id, String titulo) {
    public DetalhamentoModulo(Modulo modulo) {
        this(modulo.getId(), modulo.getTitulo());
    }


}






