
package fly.be.flyflix.conteudo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "modulos")
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String titulo;

    @NotNull
    @Column(nullable = false)
    private Integer ordem;

    // Relacionamento muitos-para-muitos com Curso
    // É importante que o lado "dono" do relacionamento tenha o @JoinTable definido,
    // e aqui usamos mappedBy para indicar que o dono está na entidade Curso
    @Builder.Default
    @ManyToMany(mappedBy = "modulos", fetch = FetchType.LAZY)
    private List<Curso> cursos = new ArrayList<>();

    // Relacionamento um-para-muitos com Aula
    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("ordem ASC")
    @Builder.Default
    private List<Aula> aulas = new ArrayList<>();

    public void adicionarAula(Aula aula) {
        aulas.add(aula);
        aula.setModulo(this);
    }

    public void removerAula(Aula aula) {
        aulas.remove(aula);
        aula.setModulo(null);
    }
}


