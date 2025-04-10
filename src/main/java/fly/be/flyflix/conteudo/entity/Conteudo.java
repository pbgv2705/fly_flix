package fly.be.flyflix.conteudo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "conteudos")
public class Conteudo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // vídeo, texto, artigo, pdf

    private String url; // link do conteúdo (ex: S3, local ou CDN)

    private Integer tempoEstimadoLeitura; // em minutos

    @Lob
    private String texto; // conteúdo em Markdown ou HTML

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aula_id")
    private Aula aula;

    @Column(nullable = false)
    private boolean ativo = true;

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    // Getters e Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public Integer getTempoEstimadoLeitura() { return tempoEstimadoLeitura; }

    public void setTempoEstimadoLeitura(Integer tempoEstimadoLeitura) { this.tempoEstimadoLeitura = tempoEstimadoLeitura; }

    public String getTexto() { return texto; }

    public void setTexto(String texto) { this.texto = texto; }

    public Aula getAula() { return aula; }

    public void setAula(Aula aula) { this.aula = aula; }
}
