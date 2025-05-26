package fly.be.flyflix.auth.controller.dto.admin;

public record DadosAdminResponse(
        Long id,
        String nome,
        String email,
        String cpf,
        boolean ativo
) {}
