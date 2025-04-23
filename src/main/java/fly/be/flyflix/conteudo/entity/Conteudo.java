package fly.be.flyflix.conteudo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "conteudos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Conteudo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoConteudo tipo; // 🎥 📄 ❓

    private String url; // Link para vídeo, PDF, etc. (pode ser S3, CDN ou local)

    private Integer tempoEstimadoLeitura; // Em minutos (aplicável para texto/PDF)

    @Lob
    private String texto; // HTML ou Markdown (para conteúdos diretamente embutidos)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aula_id", nullable = false)
    private Aula aula;

    @Column(nullable = false)
    @Builder.Default
    private boolean ativo = true;

    public enum TipoConteudo {
        VIDEO, PDF, QUIZ, TEXTO, ARTIGO
    }
}

