package fly.be.flyflix.auth.entity;

import fly.be.flyflix.auth.enums.PerfilAluno;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Aluno extends Usuario {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true) // email único e obrigatório
    private String email;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil_aluno", nullable = false)
    private PerfilAluno perfilAluno;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "curso_id")
    private Long cursoId;

    public boolean inativar() {
        if (Boolean.TRUE.equals(this.ativo)) {
            this.ativo = false;
            return true;
        }
        return false;
    }

    public boolean ativar() {
        if (Boolean.FALSE.equals(this.ativo)) {
            this.ativo = true;
            return true;
        }
        return false;
    }

    @Override
    public String getRole() {
        return "ALUNO";
    }
}
