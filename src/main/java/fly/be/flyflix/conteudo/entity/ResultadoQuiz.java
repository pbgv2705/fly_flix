package fly.be.flyflix.conteudo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class ResultadoQuiz {

    // Getters e Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long alunoId;
    private Long cursoId;
    private double nota;

    public ResultadoQuiz(Long id, Long alunoId, double nota) {
        this.id = id;
        this.alunoId = alunoId;
        this.nota = nota;
    }

    public ResultadoQuiz() {
    }

    public void setData(LocalDate now) {
    }
}

