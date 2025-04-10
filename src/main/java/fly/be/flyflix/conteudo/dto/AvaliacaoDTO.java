package fly.be.flyflix.conteudo.dto;

public class AvaliacaoDTO {
    public record NovaAvaliacaoDTO(Long cursoId, int nota, Long usuarioId) {}

}
