package fly.be.flyflix.conteudo.dto.aula;

import fly.be.flyflix.conteudo.entity.Aula;
import jakarta.validation.constraints.*;

public record CadastroAula(
        String titulo,
        String tipo,
        Integer ordem,
        Integer duracaoEstimada,
        String linkConteudo,
        Long moduloId
) {}