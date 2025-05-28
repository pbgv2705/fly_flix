package fly.be.flyflix.conteudo.dto.modulo;

import fly.be.flyflix.conteudo.dto.aula.DadosDetalhamentoAula;
import fly.be.flyflix.conteudo.entity.Modulo;

import java.util.List;
import java.util.stream.Collectors;

public record DetalhamentoModulo(Long id, String titulo, Integer ordem, Long cursoId) {}
