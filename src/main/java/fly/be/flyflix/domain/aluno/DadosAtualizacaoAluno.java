package fly.be.flyflix.domain.aluno;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoAluno(
        @NotNull
        Long id,
        String nome,
        String email,
        String endereco,
        String cep,
        String identidadeGenero,
        String orientacaoSexual) {
}
