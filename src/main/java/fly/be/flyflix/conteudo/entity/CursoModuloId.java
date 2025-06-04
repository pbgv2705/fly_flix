package fly.be.flyflix.conteudo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Embeddable
public class CursoModuloId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "curso_id", nullable = false)
    private Long cursoId;

    @Column(name = "modulo_id", nullable = false)
    private Long moduloId;

    public CursoModuloId() {}

    public CursoModuloId(Long cursoId, Long moduloId) {
        this.cursoId = cursoId;
        this.moduloId = moduloId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CursoModuloId that)) return false;
        return Objects.equals(cursoId, that.cursoId) &&
                Objects.equals(moduloId, that.moduloId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cursoId, moduloId);
    }
}
