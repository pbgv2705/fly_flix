package fly.be.flyflix.conteudo.dto.curso;

import fly.be.flyflix.conteudo.entity.Curso;

import java.time.LocalDate;
import java.util.List;

public class CursoResumoDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDate dataPublicacao;
    private Integer visualizacoes;
    private List<String> tags;
    private Double mediaAvaliacoes;

    public CursoResumoDTO(Curso curso) {
        this.id = curso.getId();
        this.titulo = curso.getTitulo();
        this.descricao = curso.getDescricao();
        this.dataPublicacao = curso.getDataPublicacao();
        this.visualizacoes = curso.getVisualizacoes();
        this.tags = curso.getTags();
        this.mediaAvaliacoes = curso.getMediaAvaliacoes();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public Integer getVisualizacoes() {
        return visualizacoes;
    }

    public void setVisualizacoes(Integer visualizacoes) {
        this.visualizacoes = visualizacoes;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Double getMediaAvaliacoes() {
        return mediaAvaliacoes;
    }

    public void setMediaAvaliacoes(Double mediaAvaliacoes) {
        this.mediaAvaliacoes = mediaAvaliacoes;
    }
}
