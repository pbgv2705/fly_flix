package fly.be.flyflix.domain.aluno;

import java.util.Date;

public record DadosDetalhamentoAluno (String cpf, String nome, String email, Date dataNascimento ) {
    public DadosDetalhamentoAluno(Aluno aluno) {
        this(aluno.getCpf(), aluno.getNome(), aluno.getEmail(),aluno.getDataNascimento());
    }
}
