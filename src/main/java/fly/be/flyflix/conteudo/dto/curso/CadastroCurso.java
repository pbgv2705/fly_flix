package fly.be.flyflix.conteudo.dto.curso;

import fly.be.flyflix.conteudo.dto.modulo.CadastroModulo;
import java.util.List;

public record CadastroCurso(
        String titulo,
        String descricao,
        String imagemCapa,

        String nivel,
        Long autorId,
        List<CadastroModulo> modulos // Novo campo para módulos com aulas
) {}
