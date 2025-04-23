
package fly.be.flyflix.conteudo.dto.aula;


import fly.be.flyflix.conteudo.dto.ConteudoDTO;

import java.util.List;

public record DadosAtualizacaoAula(
        Long id,
        String titulo,
        Integer ordem,
        Integer duracaoEstimada,
        Long moduloId,
        List<ConteudoDTO> conteudos // <-- Lista de conteúdos atualizados
) {}

