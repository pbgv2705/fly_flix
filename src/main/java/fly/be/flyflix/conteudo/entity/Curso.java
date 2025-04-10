package fly.be.flyflix.conteudo.entity;

import fly.be.flyflix.auth.entity.Usuario;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private LocalDate dataPublicacao; // 🗓️ Data de lançamento

    private Integer visualizacoes = 0; // 👁️ Contador de views

    private String imagemCapa;

    @ElementCollection
    @CollectionTable(name = "curso_tags", joinColumns = @JoinColumn(name = "curso_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>(); // 🏷️ Categorias ou palavras-chave;

    private String nivel; // iniciante, intermediario, avancado

    private Long autorId; // ID vindo do microserviço de usuários

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Modulo> modulos;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avaliacao> avaliacoes = new ArrayList<>();

    private Double mediaAvaliacoes = 0.0; // ⭐ Média das avaliações

    private Integer totalAvaliacoes = 0; // 📊 Quantidade de avaliações recebidas

    @ManyToMany
    @JoinTable(
            name = "curso_usuario_assistiu",
            joinColumns = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> usuariosQueAssistiram = new HashSet<>(); // 👥 Para recomendações

    // Getters e Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getImagemCapa() { return imagemCapa; }

    public void setImagemCapa(String imagemCapa) { this.imagemCapa = imagemCapa; }

    public List<String> getTags() { return tags; }

    public void setTags(List<String> tags) { this.tags = tags; }

    public String getNivel() { return nivel; }

    public void setNivel(String nivel) { this.nivel = nivel; }

    public Long getAutorId() { return autorId; }

    public void setAutorId(Long autorId) { this.autorId = autorId; }

    public List<Modulo> getModulos() { return modulos; }

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

    public Double getMediaAvaliacoes() {
        return mediaAvaliacoes;
    }

    public void setMediaAvaliacoes(Double mediaAvaliacoes) {
        this.mediaAvaliacoes = mediaAvaliacoes;
    }

    public Integer getTotalAvaliacoes() {
        return totalAvaliacoes;
    }

    public void setTotalAvaliacoes(Integer totalAvaliacoes) {
        this.totalAvaliacoes = totalAvaliacoes;
    }

    public Set<Usuario> getUsuariosQueAssistiram() {
        return usuariosQueAssistiram;
    }

    public void setUsuariosQueAssistiram(Set<Usuario> usuariosQueAssistiram) {
        this.usuariosQueAssistiram = usuariosQueAssistiram;
    }

    public void setModulos(List<Modulo> modulos) {
        this.modulos = modulos;
        if (modulos != null) {
            modulos.forEach(modulo -> modulo.setCurso(this));
        }
    }



}

