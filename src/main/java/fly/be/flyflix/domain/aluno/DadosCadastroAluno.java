package fly.be.flyflix.domain.aluno;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Date;

public record DadosCadastroAluno(
    @NotBlank
    String nome,
    @NotBlank @Email
    String email,
    @NotBlank
    String endereco,
    @NotBlank
    String cep,
    @NotBlank
    String identidadeGenero,
    @NotBlank
    String orientacaoSexual,
    @NotBlank
    String corRaca,
    @NotNull
    Date dataNascimento) {
}
