package fly.be.flyflix.conteudo.dto.curso;


import java.util.List;

public record CadastroCurso(
        String titulo,
        String descricao,
        String imagemCapa,
        List<String> tags,
        String nivel,
        Long autorId
) {}