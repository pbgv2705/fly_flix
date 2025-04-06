package fly.be.flyflix.domain.aluno;

import fly.be.flyflix.validation.CPF;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Aluno")
@Table(name = "alunos", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cpf")
})
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CPF
    @NotBlank
    @Column(nullable = false, unique = true)
    private String cpf;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;

    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @Column(nullable = false)
    private boolean ativo;

    public Aluno(DadosCadastroAluno dados) {
        this.cpf = dados.cpf();
        this.nome = dados.nome();
        this.email = dados.email();
        this.dataNascimento = dados.dataNascimento();
        this.ativo = true;
    }

    public void atualizarInformacoes(DadosAtualizacaoAluno dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.email() != null) {
            this.email = dados.email();
        }
    }

    public void inativar() {
        this.ativo = false;
    }
}
