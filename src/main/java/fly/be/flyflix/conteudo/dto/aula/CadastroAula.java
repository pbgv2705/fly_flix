package fly.be.flyflix.conteudo.dto.aula;

import fly.be.flyflix.conteudo.dto.ConteudoDTO;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public record CadastroAula(
        @NotBlank
        String titulo,

        @NotNull
        Integer ordem,

        @Min(0)
        Integer duracaoEstimada,

        @NotNull
        List<@Valid ConteudoDTO> conteudos, // Garante que cada ConteudoDTO também seja validado

        @NotNull
        Long moduloId
) {}

