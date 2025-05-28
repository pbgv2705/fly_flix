package fly.be.flyflix.conteudo.dto.curso;

import java.util.List;

public record AtualizacaoCurso(
        String titulo,
        String descricao,
        String imagemCapa,
        String nivel,
        Long autorId
) {}
