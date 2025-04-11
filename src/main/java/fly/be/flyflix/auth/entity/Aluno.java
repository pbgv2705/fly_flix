package fly.be.flyflix.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Table(name = "alunos")
@Entity(name = "Aluno")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cpf;

    @OneToOne
    //@JoinColumn(name = "cpf", referencedColumnName = "cpf") //mapear cpf do usuario e aluno
    @JoinColumn(name = "usuario_id") // mapear id do usuario com o Aluno
    private Usuario usuario;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Past(message = "Data de nascimento inválida")
    private LocalDate dataNascimento;

    //perfil do aluno
    @Enumerated(EnumType.STRING)
    @Column(name = "perfil_aluno")
    private PerfilAluno perfilAluno;

    // Quando o aluno é criado ele é ativado
    @Column(nullable = false, columnDefinition = "BOOLEAN")
    private Boolean ativo = true;

    //desativar aluno
    public boolean inativar() {
        this.ativo = false;
        return true; // Aluno inativado
    }

    // ativar aluno
    public boolean ativar() {
        if (Boolean.FALSE.equals(this.ativo)) {
            this.ativo = true;
            return true; // Aluno ativado
        }
        return false; // Aluno já estava ativado
    }

    //@ManyToMany um aluno pode estar em varios cursos e um curso pode ter varios alunos
}
