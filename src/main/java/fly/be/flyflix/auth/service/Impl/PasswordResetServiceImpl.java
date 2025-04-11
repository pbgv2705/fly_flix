package fly.be.flyflix.auth.service.Impl;

import fly.be.flyflix.auth.controller.dto.ResetarSenha;
import fly.be.flyflix.auth.controller.dto.SolicitarResetSenha;
import fly.be.flyflix.auth.entity.PasswordResetToken;
import fly.be.flyflix.auth.entity.Usuario;
import fly.be.flyflix.auth.repository.PasswordResetTokenRepository;
import fly.be.flyflix.auth.repository.UsuarioRepository;
import fly.be.flyflix.auth.service.EmailService;
import fly.be.flyflix.auth.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    @Value("${base.url}")
    private String baseUrl;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    // solicitar reset de senha gerara token valido por 1 hora
    @Override
    public ResponseEntity<Map<String, Object>> solicitarResetSenha(SolicitarResetSenha dto) {

        var usuarioEmailDB = usuarioRepository.findByLogin(dto.email());

        if (usuarioEmailDB.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Usuario nao encontrado");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        //gerar Token para reset
        String token = UUID.randomUUID().toString();
        LocalDateTime expirationDate = LocalDateTime.now().plusHours(1);

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUsuario(usuarioEmailDB.get());
        resetToken.setExpirationDate(expirationDate);

        passwordResetTokenRepository.save(resetToken);

        String link = baseUrl + "/resetar-senha?token=" + token;
        String conteudo = "<div style='text-align: center;'>"
                + "<h2><strong>Flyflix Education - Password Reset</strong></h2>"
                + "<p>Redefina sua senha <a href='" + link + "'>Clique aqui</a></p>"
                + "</div>";
        //HABLITAR SÓ EM DEPLOY sendEmail
        //emailService.sendEmail(dto.email(), "Redefinir senha", conteudo);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Verifique seu email para redefinir sua senha");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> resetarSenha(ResetarSenha dados) {

        var usuariotoken = passwordResetTokenRepository.findByToken(dados.token());

        //validar se existem algum token para reset da senha
        if (usuariotoken.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "token invalido");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        //validar se o token é valido
        if(usuariotoken.get().getExpirationDate().isBefore(LocalDateTime.now())){
            Map<String, Object> response = new HashMap<>();
            response.put("message", "token expirado");

            return ResponseEntity.status(HttpStatus.GONE).body(response);
        }

        Usuario usuario = usuariotoken.get().getUsuario();

        usuario.setSenha(passwordEncoder.encode(dados.novaSenha()));

        usuarioRepository.save(usuario);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "senha redefinida com sucesso");

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
