package fly.be.flyflix.auth.controller.dto;


import java.time.LocalDate;

public record NovoAdminRequest(String nome, String email, String cpf, LocalDate dataNascimento) {}
