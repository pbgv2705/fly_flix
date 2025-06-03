package fly.be.flyflix.conteudo.dto.modulo;

import fly.be.flyflix.conteudo.entity.Modulo;

import java.util.List;


public record DetalhamentoModulo(
        Long id,
        String titulo,
        Integer ordem

) {
    public DetalhamentoModulo(Modulo modulo) {
        this(
                modulo.getId(),
                modulo.getTitulo(),
                modulo.getOrdem()

        );
    }
}



