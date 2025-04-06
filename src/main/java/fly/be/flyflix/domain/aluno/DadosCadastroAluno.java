package fly.be.flyflix.domain.aluno;

import fly.be.flyflix.validation.CPF;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.util.Date;

public record DadosCadastroAluno(
        @NotBlank
        @CPF
        String cpf,
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,

        @NotNull
        @Past
        Date dataNascimento) {
}
