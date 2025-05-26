package fly.be.flyflix.conteudo.dto.aula;

import fly.be.flyflix.conteudo.entity.Aula;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public record DadosDetalhamentoAula(
        Long id,
        String titulo,
        Aula.TipoConteudo tipo,
        Integer ordem,
        Integer duracaoEstimada,
        Long moduloId
) {
    public static DadosDetalhamentoAula from(Aula aula) {
        return new DadosDetalhamentoAula(
                aula.getId(),
                aula.getTitulo(),
                aula.getTipo(),
                aula.getOrdem(),
                aula.getDuracaoEstimada(),
                aula.getModulo().getId()
        );
    }
}


