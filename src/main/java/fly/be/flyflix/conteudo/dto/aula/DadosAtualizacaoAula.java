
package fly.be.flyflix.conteudo.dto.aula;


public record DadosAtualizacaoAula(
        Long id,
        String titulo,
        Integer ordem,
        Integer duracaoEstimada,
        Long moduloId
) {}

