package fly.be.flyflix.auth.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        String email,

        @NotBlank
        String senha
) {
}
