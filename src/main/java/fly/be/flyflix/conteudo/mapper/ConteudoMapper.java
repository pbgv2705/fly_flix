package fly.be.flyflix.conteudo.mapper;

import fly.be.flyflix.conteudo.entity.Conteudo;
import fly.be.flyflix.conteudo.dto.ConteudoDTO;

public class ConteudoMapper {

    // Método estático para converter entidade Conteudo em ConteudoDTO
    public static ConteudoDTO toDTO(Conteudo conteudo) {
        if (conteudo == null) {
            return null; // Retorna null se a entidade for null
        }

        ConteudoDTO dto = new ConteudoDTO();
        dto.setId(conteudo.getId());
        dto.setTipo(conteudo.getTipo());
        dto.setAtivo(conteudo.isAtivo());

        // Lógica de acordo com o tipo de conteúdo
        switch (conteudo.getTipo()) {
            case VIDEO:
            case PDF:
                dto.setUrl(conteudo.getUrl()); // Para tipos que precisam de URL
                break;

            case TEXTO:
            case ARTIGO:
                dto.setTexto(conteudo.getTexto()); // Para tipos de conteúdo textual
                dto.setTempoEstimadoLeitura(conteudo.getTempoEstimadoLeitura()); // Pode ser necessário também
                break;

            case QUIZ:
                dto.setUrl(conteudo.getUrl()); // Caso o quiz tenha um link
                break;

            default:
                throw new IllegalArgumentException("Tipo de conteúdo não reconhecido: " + conteudo.getTipo());
        }

        // Adiciona o tempo de leitura, se necessário (pode ser em tipos específicos)
        if (conteudo.getTipo().equals("TEXTO") || conteudo.getTipo().equals("PDF")) {
            dto.setTempoEstimadoLeitura(conteudo.getTempoEstimadoLeitura());
        }

        return dto;
    }

    // Método para converter ConteudoDTO em Conteudo (para quando precisar persistir ou atualizar no banco)
    public static Conteudo toEntity(ConteudoDTO dto) {
        if (dto == null) {
            return null; // Retorna null se o DTO for null
        }

        Conteudo conteudo = new Conteudo();
        conteudo.setId(dto.getId());
        conteudo.setTipo(dto.getTipo());
        conteudo.setAtivo(dto.isAtivo());

        // Lógica para converter o tipo de conteúdo de volta para a entidade
        switch (dto.getTipo()) {
            case VIDEO:
            case PDF:
                conteudo.setUrl(dto.getUrl());
                break;

            case TEXTO:
            case ARTIGO:
                conteudo.setTexto(dto.getTexto());
                conteudo.setTempoEstimadoLeitura(dto.getTempoEstimadoLeitura());
                break;

            case QUIZ:
                conteudo.setUrl(dto.getUrl());
                break;

            default:
                throw new IllegalArgumentException("Tipo de conteúdo não reconhecido: " + dto.getTipo());
        }

        return conteudo;
    }
}
