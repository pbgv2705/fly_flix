package fly.be.flyflix.conteudo.dto.curso;


import java.util.List;

public record CadastroModulo(
        String titulo,
        Integer ordem,
        List<CursoComOrdem> cursosComOrdem
) { }

