package fly.be.flyflix.conteudo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "aulas") // <- se o banco usa "aulas"
@ToString(onlyExplicitlyIncluded = true)
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoConteudo tipo;

    @NotNull
    @Column(nullable = false)
    private Integer ordem;

    @PositiveOrZero
    private Integer duracaoEstimada; // em minutos
    private String linkConteudo; // URL para vídeo, PDF, etc.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modulo_id", nullable = false)
    private Modulo modulo;

    public void setCurso(Curso curso) {
    }

    public enum TipoConteudo {
        VIDEO, TEXTO, ARTIGO, QUIZ, PDF
    }
}
