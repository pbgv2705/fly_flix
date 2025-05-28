package fly.be.flyflix.conteudo.dto.aula;

import fly.be.flyflix.conteudo.entity.Aula;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public record DadosDetalhamentoAula(
        Long id,
        String titulo,
        String tipo,
        Integer ordem,
        Integer duracaoEstimada,
        String linkConteudo,
        Long moduloId,
        String urlCapa
) {}


