package fly.be.flyflix.conteudo.entity;

import fly.be.flyflix.auth.entity.Usuario;
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
        @Index(name = "idx_curso_data_publicacao", columnList = "data_publicacao")
})
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;


    @NotNull
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    private String imagemCapa;

    @Builder.Default
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "curso_modulo",
            joinColumns = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "modulo_id")
    )
    private List<Modulo> modulos = new ArrayList<>();


    // Métodos de negócio
    public void adicionarModulo(Modulo modulo) {
        modulos.add(modulo);
        modulo.getCursos().add(this);
    }

    public void removerModulo(Modulo modulo) {
        modulos.remove(modulo);
        modulo.getCursos().remove(this);
    }
}