package fly.be.flyflix.conteudo.controller.dto;
import fly.be.flyflix.conteudo.entity.Categoria;
import fly.be.flyflix.conteudo.entity.Conteudo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroConteudo(
        @NotBlank
        String nome,
        @NotBlank
        String descricao,
        @NotBlank
        String duracao,
        @NotBlank
        String link,
        @NotNull
        Categoria categoria) {
    public DadosCadastroConteudo(Conteudo conteudo) {
        this( conteudo.getNome(), conteudo.getDescricao(), conteudo.getDuracao(), conteudo.getLink(), conteudo.getCategoria());
    }
}
