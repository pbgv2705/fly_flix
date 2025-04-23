package fly.be.flyflix.auth.controller.dto;

import fly.be.flyflix.auth.entity.Aluno;
import fly.be.flyflix.auth.entity.PerfilAluno;
import fly.be.flyflix.auth.entity.Usuario;

import java.time.LocalDate;
import java.util.Date;

public record DadosDetalhamentoAluno (Long id, String cpf, String nome, String email, LocalDate dataNascimento, Boolean ativo, PerfilAluno perfilAluno) {
    public DadosDetalhamentoAluno(Aluno aluno) {
        this(
                aluno.getId(),
                aluno.getCpf(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getDataNascimento(),
                aluno.getAtivo(),
                aluno.getPerfilAluno()
        );
    }
}
