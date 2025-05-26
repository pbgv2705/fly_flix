package fly.be.flyflix.auth.service;

import fly.be.flyflix.auth.repository.AlunoRepository;
import fly.be.flyflix.auth.repository.PasswordResetTokenRepository;
import fly.be.flyflix.auth.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    public UsuarioService(UsuarioRepository usuarioRepository, AlunoRepository alunoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.alunoRepository = alunoRepository;
    }


    // ========================================
    // SENHAS
    // ========================================

    public ResponseEntity<Map<String, String>> resetarSenha(String login) {
        return usuarioRepository.findByEmail(login)
                .map(usuario -> {
                    String novaSenha = UUID.randomUUID().toString().substring(0, 8);
                    usuario.setSenha(passwordEncoder.encode(novaSenha));
                    usuarioRepository.save(usuario);
                    emailService.enviarEmail(
                            usuario.getEmail(),
                            "Redefinição de senha FlyFlix",
                            "Sua nova senha temporária é:\n\n" + novaSenha + "\n\nAltere-a após o login."
                    );
                    return ResponseEntity.ok(Map.of("message", "Nova senha enviada por email"));
                })
                .orElseGet(() -> ResponseEntity.badRequest().body(Map.of("error", "Usuário não encontrado")));
    }
}
