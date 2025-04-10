
package fly.be.flyflix.conteudo.entity;

import jakarta.persistence.*;
        import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int nota; // ex: 1 a 5

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    private Long usuarioId; // você pode usar entidade Usuario se quiser
}
