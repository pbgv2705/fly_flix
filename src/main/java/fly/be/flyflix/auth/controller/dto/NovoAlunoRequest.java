package fly.be.flyflix.auth.controller.dto;


import java.time.LocalDate;

public record NovoAlunoRequest(String nome, String email, String cpf, LocalDate dataNascimento, Long cursoId) {}
