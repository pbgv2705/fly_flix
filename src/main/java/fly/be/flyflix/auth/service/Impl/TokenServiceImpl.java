package fly.be.flyflix.auth.service.Impl;

import fly.be.flyflix.auth.controller.dto.LoginRequest;
import fly.be.flyflix.auth.controller.dto.LoginResponse;
import fly.be.flyflix.auth.entity.Aluno;
import fly.be.flyflix.auth.entity.Usuario;
import fly.be.flyflix.auth.repository.AlunoRepository;
import fly.be.flyflix.auth.repository.UsuarioRepository;
import fly.be.flyflix.auth.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {


    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final AlunoRepository alunoRepository;

    public TokenServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder, AlunoRepository alunoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.alunoRepository = alunoRepository;
    }


    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(loginRequest.login());

        if (usuarioOpt.isEmpty() || !usuarioOpt.get().isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("User or password is invalid!");
        }

        Usuario usuario = usuarioOpt.get();
        Instant now = Instant.now();
        long expiresIn = 604800L; // 1 semana

        // Token JWT com role única baseada na subclasse
        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer("flyflix-backend")
                .subject(usuario.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", "SCOPE_" + usuario.getRole());

        // Se for um Aluno, adicionar perfilAluno como claim extra
        if (usuario instanceof Aluno aluno) {
            claimsBuilder.claim("allowedCategories", aluno.getPerfilAluno());
        }

        String jwt = jwtEncoder.encode(JwtEncoderParameters.from(claimsBuilder.build())).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwt, expiresIn)).getBody();
    }

    @Override
    public String gerarTokenRedefinicaoSenha(Aluno aluno) {
        return null;
    }

    @Override
    public Aluno validarTokenRedefinicaoSenha(String token) {
        return null;
    }

    @Override
    public void invalidarToken(String token) {

    }
}
