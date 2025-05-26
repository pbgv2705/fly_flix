package fly.be.flyflix.conteudo.dto.aula;

import jakarta.validation.constraints.*;

public record CadastroAula(
        @NotBlank
        String titulo,

        @NotNull
        Integer ordem,

        @Min(0)
        Integer duracaoEstimada,

        @NotNull
        Long moduloId
) {}

