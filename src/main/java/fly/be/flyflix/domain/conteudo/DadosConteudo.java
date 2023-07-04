package fly.be.flyflix.domain.conteudo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosConteudo(
        @NotNull
        Long id,
        @NotBlank
        String nome,
        @NotBlank
        String descricao,
        @NotBlank
        String duracao,
        @NotBlank
        String link,
        @NotBlank
        Categoria categoria) {
    public DadosConteudo(Conteudo conteudo) {
        this(conteudo.getId(), conteudo.getNome(), conteudo.getDescricao(), conteudo.getDuracao(), conteudo.getLink(), conteudo.getCategoria());
    }
}
