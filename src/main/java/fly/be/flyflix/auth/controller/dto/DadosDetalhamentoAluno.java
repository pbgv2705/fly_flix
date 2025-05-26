package fly.be.flyflix.auth.controller.dto;

import fly.be.flyflix.auth.entity.Aluno;
import fly.be.flyflix.auth.enums.PerfilAluno;

import java.time.LocalDate;

public record DadosDetalhamentoAluno(
        Long id,
        String cpf,
        String nome,
        String email,
        LocalDate dataNascimento,
        Boolean ativo,
        PerfilAluno perfilAluno
) {
    // Construtor que aceita Aluno e inicializa todos os campos do record
    public DadosDetalhamentoAluno(Aluno aluno) {
        this(
                aluno.getId(),
                aluno.getCpf(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getDataNascimento(),
                aluno.getAtivo(),
                aluno.getPerfilAluno()  // Certifique-se que o método existe em Aluno
        );
    }
}
