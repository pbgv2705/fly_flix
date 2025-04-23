package fly.be.flyflix.conteudo.dto.aula;

import fly.be.flyflix.conteudo.dto.ConteudoDTO;
import fly.be.flyflix.conteudo.entity.Aula;
import fly.be.flyflix.conteudo.entity.Aula.TipoConteudo;
import fly.be.flyflix.conteudo.mapper.ConteudoMapper;

import java.util.List;

public record DadosDetalhamentoAula(
        Long id,
        String titulo,
        Aula.TipoConteudo tipo,
        Integer ordem,
        Integer duracaoEstimada,
        List<ConteudoDTO> conteudos,
        Long moduloId
) {
    public DadosDetalhamentoAula(Aula aula) {
        this(
                aula.getId(),
                aula.getTitulo(),
                aula.getTipo(),
                aula.getOrdem(),
                aula.getDuracaoEstimada(),
                aula.getConteudos().stream()
                        .map(ConteudoMapper::toDTO)
                        .toList(),
                aula.getModulo().getId()
        );
    }


}


