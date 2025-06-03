package fly.be.flyflix.conteudo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class ProgressoAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private Long alunoId;

    @Setter
    private Long aulaId;

    @Setter
    private boolean assistida;

    @Setter
    private Long cursoId;

    public ProgressoAluno(Long alunoId, Long aulaId, Long cursoId, boolean assistida) {
        this.alunoId = alunoId;
        this.aulaId = aulaId;
        this.cursoId = cursoId;
        this.assistida = assistida;
    }

}

