package fly.be.flyflix.auth.controller.dto;

import fly.be.flyflix.auth.entity.Aluno;
import fly.be.flyflix.auth.entity.Usuario;

import java.time.LocalDate;
import java.util.Date;

public record DadosDetalhamentoAluno (Long id, String cpf, Usuario usuario, String nome, String email, LocalDate dataNascimento ) {
    public DadosDetalhamentoAluno(Aluno aluno) {
        this(aluno.getId(), aluno.getCpf(), aluno.getUsuario(), aluno.getNome(), aluno.getEmail(), aluno.getDataNascimento());
    }
}
