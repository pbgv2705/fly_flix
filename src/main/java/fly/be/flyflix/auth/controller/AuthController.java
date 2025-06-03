package fly.be.flyflix.auth.controller;

import fly.be.flyflix.auth.controller.dto.LoginRequest;
import fly.be.flyflix.auth.controller.dto.LoginResponse;
import fly.be.flyflix.auth.entity.Aluno;
import fly.be.flyflix.auth.repository.AlunoRepository;
import fly.be.flyflix.auth.service.EmailService;
import fly.be.flyflix.auth.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final TokenService tokenService;
    private final AlunoRepository alunoRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(TokenService tokenService,
                          AlunoRepository alunoRepository,
                          EmailService emailService,
                          PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.alunoRepository = alunoRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/esqueci-senha")
    public ResponseEntity<?> esqueciSenha(@RequestBody Map<String, String> body) {
        String email = body.get("email");

        Optional<Aluno> alunoOpt = alunoRepository.findByEmail(email);
        if (alunoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Email não cadastrado.");
        }

        Aluno aluno = alunoOpt.get();
        String token = tokenService.gerarTokenRedefinicaoSenha(aluno);

        String link = "http://localhost:3000/resetar-senha?token=" + token;
        emailService.enviarEmail(email, "Redefinição de senha Flyflix",
                "Use esse link para redefinir sua senha: " + link);

        return ResponseEntity.ok("Email enviado para redefinição de senha.");
    }

    @PostMapping("/resetar-senha")
    public ResponseEntity<?> resetarSenha(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String novaSenha = body.get("novaSenha");

        try {
            Aluno aluno = tokenService.validarTokenRedefinicaoSenha(token);
            aluno.setSenha(passwordEncoder.encode(novaSenha));
            alunoRepository.save(aluno);
            tokenService.invalidarToken(token);

            return ResponseEntity.ok("Senha alterada com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            LoginResponse response = tokenService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Credenciais inválidas.");
        }
    }
}
