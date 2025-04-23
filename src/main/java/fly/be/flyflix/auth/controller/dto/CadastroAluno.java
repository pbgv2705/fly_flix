package fly.be.flyflix.auth.controller.dto;

import fly.be.flyflix.auth.entity.PerfilAluno;
import fly.be.flyflix.auth.entity.PerfilUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;


public record CadastroAluno(
        @NotBlank
        String nome,

        @NotBlank
        @Email(message = "O email deve ser válido")
        String email,

        @NotBlank
        String senha,

        //cpf
        @CPF
        String cpf,

        @NotNull
        @Past (message = "Data de nascimento inválida")
        LocalDate dataNascimento,

        @NotNull
        String perfil,

        @NotNull
        String perfilAluno

) {
}
