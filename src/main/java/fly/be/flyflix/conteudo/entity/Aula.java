package fly.be.flyflix.conteudo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "aulas")
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String tipo; // vídeo, texto, artigo, quiz

    private Integer ordem;

    private Integer duracaoEstimada; // em minutos

    private String linkConteudo; // URL, pode ser link para vídeo ou outro tipo de conteúdo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modulo_id")
    private Modulo modulo;

    // Getters e Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public Integer getOrdem() { return ordem; }

    public void setOrdem(Integer ordem) { this.ordem = ordem; }

    public Integer getDuracaoEstimada() { return duracaoEstimada; }

    public void setDuracaoEstimada(Integer duracaoEstimada) { this.duracaoEstimada = duracaoEstimada; }

    public String getLinkConteudo() { return linkConteudo; }

    public void setLinkConteudo(String linkConteudo) { this.linkConteudo = linkConteudo; }

    public Modulo getModulo() { return modulo; }

    public void setModulo(Modulo modulo) { this.modulo = modulo; }
}

