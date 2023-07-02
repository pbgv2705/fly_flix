package fly.be.flyflix.domain.aluno;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;


@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Aluno")
@Table(name = "aluno")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String endereco;
    private String cep;
    private String identidadeGenero;
    private String orientacaoSexual;
    private String corRaca;
    private Date dataNascimento;

    public Aluno(DadosCadastroAluno dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.endereco = dados.endereco();
        this.cep = dados.cep();
        this.identidadeGenero = dados.identidadeGenero();
        this.orientacaoSexual = dados.orientacaoSexual();
        this.corRaca = dados.corRaca();
        this.dataNascimento = dados.dataNascimento();
    }
}
