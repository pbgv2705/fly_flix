package fly.be.flyflix.domain.aluno;

import java.util.Date;

public record DadosDetalhamentoAluno (Long id, String nome, String email, String endereco, String cep,
      String identidadeGenero, String orientacaoSexual, String corRaca, Date dataNascimento ) {
    public DadosDetalhamentoAluno(Aluno aluno) {
        this(aluno.getId(), aluno.getNome(), aluno.getEmail(), aluno.getEndereco(), aluno.getCep(), aluno.getIdentidadeGenero(), aluno.getOrientacaoSexual(), aluno.getCorRaca(), aluno.getDataNascimento());
    }
}
