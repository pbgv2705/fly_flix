package fly.be.flyflix.conteudo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "cursos", indexes = {
        @Index(name = "idx_curso_data_publicacao", columnList = "data_publicacao"),
        @Index(name = "idx_curso_nivel", columnList = "nivel"),
        @Index(name = "idx_curso_autor_id", columnList = "autor_id")
})
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Column(nullable = false)
    @Builder.Default
    private Integer visualizacoes = 0;

    private String imagemCapa;

    @ElementCollection
    @CollectionTable(name = "curso_tags", joinColumns = @JoinColumn(name = "curso_id"))
    @Column(name = "tag")
    @Builder.Default
    private List<String> tags = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private NivelCurso nivel;

    @NotNull
    @Column(name = "autor_id", nullable = false)
    private Long autorId;

    @Builder.Default
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Modulo> modulos = new ArrayList<>();

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Avaliacao> avaliacoes = new ArrayList<>();

    @Column(nullable = false)
    @Builder.Default
    private Double mediaAvaliacoes = 0.0;

    @Column(nullable = false)
    private Integer totalAvaliacoes = 0;

    // Recomendação: Evitar ManyToMany com outra entidade de microserviço
    // Substituído por lista de IDs dos usuários
    @ElementCollection
    @CollectionTable(name = "curso_usuario_ids", joinColumns = @JoinColumn(name = "curso_id"))
    @Column(name = "usuario_id")
    @Builder.Default
    private Set<Long> usuariosQueAssistiramIds = new HashSet<>();

    // Métodos de negócio
    public void adicionarModulo(Modulo modulo) {
        modulos.add(modulo);
        modulo.setCurso(this);
    }

    public void removerModulo(Modulo modulo) {
        modulos.remove(modulo);
        modulo.setCurso(null);
    }

    public enum NivelCurso {
        INICIANTE, INTERMEDIARIO, AVANCADO
    }
}
