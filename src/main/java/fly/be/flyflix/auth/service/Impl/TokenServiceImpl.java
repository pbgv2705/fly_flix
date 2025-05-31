package fly.be.flyflix.auth.service.Impl;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fly.be.flyflix.auth.controller.dto.LoginRequest;
import fly.be.flyflix.auth.controller.dto.LoginResponse;
import fly.be.flyflix.auth.entity.Aluno;
import fly.be.flyflix.auth.entity.Usuario;
import fly.be.flyflix.auth.repository.AlunoRepository;
import fly.be.flyflix.auth.repository.UsuarioRepository;
import fly.be.flyflix.auth.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {
    private final JwtDecoder jwtDecoder;
    private final Set<String> tokenBlacklist = new HashSet<>();
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final AlunoRepository alunoRepository;

    private final AuthenticationManager authenticationManager;

    public TokenServiceImpl(
            JwtDecoder jwtDecoder, UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtEncoder jwtEncoder,
            AlunoRepository alunoRepository,
            AuthenticationManager authenticationManager // <-- novo
    ) {
        this.jwtDecoder = jwtDecoder;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.alunoRepository = alunoRepository;
        this.authenticationManager = authenticationManager; // <-- atribuição
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        // Autenticação com Spring Security
        var authToken = new UsernamePasswordAuthenticationToken(
                loginRequest.email(), loginRequest.senha()
        );
        var authentication = authenticationManager.authenticate(authToken);

        // Aqui o usuário está autenticado
        Usuario usuario = (Usuario) authentication.getPrincipal();

        Instant now = Instant.now();
        long expiresIn = 7776000L; // 90 dias

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer("flyflix-backend")
                .subject(usuario.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("authorities", List.of("ROLE_" + usuario.getRole()));
        //.claim("scope", "SCOPE_" + usuario.getRole());
        if (usuario instanceof Aluno aluno) {
            claimsBuilder.claim("allowedCategories", aluno.getPerfilAluno());
        }
        String jwt = jwtEncoder.encode(JwtEncoderParameters.from(claimsBuilder.build())).getTokenValue();
        return new LoginResponse(jwt, expiresIn);
    }

    @Override
    public void invalidarToken(String token) {
        tokenBlacklist.add(token);
    }

    @Override
    public String gerarTokenRedefinicaoSenha(Aluno aluno) {
        Instant now = Instant.now();
        long expiresIn = Duration.ofHours(1).getSeconds(); // 1 hora
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("flyflix-password-reset")
                .subject(aluno.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("type", "password-reset")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
    @Override
    public Aluno validarTokenRedefinicaoSenha(String token) {
        try {
            if (tokenBlacklist.contains(token)) {
                throw new RuntimeException("Token já foi utilizado ou está inválido");
            }

            Jwt decoded = jwtDecoder.decode(token);

            if (!"password-reset".equals(decoded.getClaimAsString("type"))) {
                throw new RuntimeException("Tipo de token inválido");
            }

            Long alunoId = Long.parseLong(decoded.getSubject());

            return alunoRepository.findById(alunoId)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Token inválido ou expirado");
        }
    }

}
