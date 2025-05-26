package fly.be.flyflix.auth.controller;

import fly.be.flyflix.auth.controller.dto.*;
import fly.be.flyflix.auth.controller.dto.aluno.AtualizarAlunoRequest;
import fly.be.flyflix.auth.service.PasswordResetService;
import fly.be.flyflix.auth.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final PasswordResetService passwordResetService;

    private final UsuarioService usuarioService;

    public UsuarioController(PasswordResetService passwordResetService, UsuarioService usuarioService) {
        this.passwordResetService = passwordResetService;
        this.usuarioService = usuarioService;
    }



    // Resetar senha (reset manual ou primeiro login)
    @PostMapping("/resetar-senha")
    public ResponseEntity<?> resetarSenha(@RequestBody RequisicaoResetSenhaDTO dados) {
        return usuarioService.resetarSenha(dados.email());
    }



    // Requisição para solicitar link de redefinição de senha (esqueci minha senha)
    // Solicita envio de e-mail com link de redefinição
    @PostMapping("/esqueci-senha")
    public ResponseEntity<Map<String, Object>> solicitarResetSenha(@RequestBody RequisicaoResetSenhaDTO dto) {
        return passwordResetService.solicitarResetSenha(dto);
    }

    // Redefinir senha usando token
    // Redefine a senha com token
    @PostMapping("/redefinir-senha")
    public ResponseEntity<Map<String, Object>> redefinirSenha(@RequestBody RedefinicaoSenhaDTO dto) {
        return passwordResetService.redefinirSenha(dto);
    }
}
