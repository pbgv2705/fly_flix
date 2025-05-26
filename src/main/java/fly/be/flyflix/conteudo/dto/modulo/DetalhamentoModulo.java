package fly.be.flyflix.conteudo.dto.modulo;

import fly.be.flyflix.conteudo.dto.aula.DadosDetalhamentoAula;
import fly.be.flyflix.conteudo.entity.Modulo;

import java.util.List;
import java.util.stream.Collectors;

public record DetalhamentoModulo(
        Long id,
        String titulo,
        Integer ordem,
        Long cursoId,
        List<DadosDetalhamentoAula> aulas
) {
    // Construtor para mapear a entidade Modulo para DetalhamentoModulo
    public DetalhamentoModulo(Modulo modulo) {
        this(
                modulo.getId(),
                modulo.getTitulo(),
                modulo.getOrdem(),
                modulo.getCurso().getId(),
                // Convertendo a lista de aulas do módulo para a lista de DadosDetalhamentoAula
                modulo.getAulas().stream()
                        .map(DadosDetalhamentoAula::from)
                        .collect(Collectors.toList())
        );
    }


}


