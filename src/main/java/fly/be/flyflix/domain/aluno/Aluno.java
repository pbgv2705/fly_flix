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
@Table(name = "alunos")
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
    private boolean ativo;

    public Aluno(DadosCadastroAluno dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.endereco = dados.endereco();
        this.cep = dados.cep();
        this.identidadeGenero = dados.identidadeGenero();
        this.orientacaoSexual = dados.orientacaoSexual();
        this.corRaca = dados.corRaca();
        this.dataNascimento = dados.dataNascimento();
        this.ativo = true;
    }

    public void atualizarInformacoes(DadosAtualizacaoAluno dados){
        if (dados.nome()!=null) {
            this.nome = dados.nome();
        }
        if (dados.email()!=null) {
            this.email = dados.email();
        }
        if (dados.cep()!=null) {
            this.cep = dados.cep();
        }
        if (dados.identidadeGenero()!=null) {
            this.identidadeGenero = dados.identidadeGenero();
        }
        if (dados.orientacaoSexual()!=null) {
            this.orientacaoSexual = dados.orientacaoSexual();
        }
        if (dados.endereco()!=null) {
            this.endereco = dados.endereco();
        }
    }

    public void inativar() {
        this.ativo = false;
    }
}
