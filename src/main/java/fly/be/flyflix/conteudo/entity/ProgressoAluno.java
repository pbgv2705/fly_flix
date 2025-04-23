package fly.be.flyflix.conteudo.entity;

import jakarta.persistence.*;

@Entity
public class ProgressoAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long alunoId;

    private Long aulaId;

    private boolean assistida;

    private Long cursoId;

    public ProgressoAluno() {}

    public ProgressoAluno(Long alunoId, Long aulaId, Long cursoId, boolean assistida) {
        this.alunoId = alunoId;
        this.aulaId = aulaId;
        this.cursoId = cursoId;
        this.assistida = assistida;
    }

    public ProgressoAluno(Long alunoIdd, Long aulaId, boolean b) {
    }

    public Long getId() {
        return id;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public Long getAulaId() {
        return aulaId;
    }

    public void setAulaId(Long aulaId) {
        this.aulaId = aulaId;
    }

    public boolean isAssistida() {
        return assistida;
    }

    public void setAssistida(boolean assistida) {
        this.assistida = assistida;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }
}

