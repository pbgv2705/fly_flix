
package fly.be.flyflix.auth.controller.dto.senha;

import jakarta.validation.constraints.NotBlank;

public record AtualizarSenhaDTO(
        @NotBlank String email,
        @NotBlank String senhaAtual,
        @NotBlank String novaSenha
) {}
