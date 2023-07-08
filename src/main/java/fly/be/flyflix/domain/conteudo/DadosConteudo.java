package fly.be.flyflix.domain.conteudo;

import jakarta.validation.constraints.NotNull;

public record DadosConteudo(
        @NotNull
        Long id,
        String nome,
        String descricao,
        String duracao,
        String link,
        Categoria categoria) {
    public DadosConteudo(Conteudo conteudo) {
        this(conteudo.getId(), conteudo.getNome(), conteudo.getDescricao(), conteudo.getDuracao(), conteudo.getLink(), conteudo.getCategoria());
    }
}
