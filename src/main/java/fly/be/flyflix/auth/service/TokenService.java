package fly.be.flyflix.auth.service;

import fly.be.flyflix.auth.controller.dto.LoginRequest;
import fly.be.flyflix.auth.controller.dto.LoginResponse;
import fly.be.flyflix.auth.entity.Aluno;

public interface TokenService {
    LoginResponse login(LoginRequest loginRequest);
    String gerarTokenRedefinicaoSenha(Aluno aluno);
    Aluno validarTokenRedefinicaoSenha(String token);
    void invalidarToken(String token);
}
