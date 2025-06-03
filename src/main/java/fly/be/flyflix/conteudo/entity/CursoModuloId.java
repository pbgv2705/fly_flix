package fly.be.flyflix.conteudo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CursoModuloId implements Serializable {

    @Column(name = "curso_id")
    private Long cursoId;

    @Column(name = "modulo_id")
    private Long moduloId;

}
