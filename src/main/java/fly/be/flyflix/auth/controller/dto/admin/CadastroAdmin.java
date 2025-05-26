
package fly.be.flyflix.auth.controller.dto.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CadastroAdmin(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String cpf
) {}
