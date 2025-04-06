package fly.be.flyflix.domain.aluno;

import fly.be.flyflix.validation.CPF;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoAluno(
        @NotNull Long id,
        @NotNull
        @CPF
        String cpf,
        String nome,
        @Email
        String email

        ) {}
