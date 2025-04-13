package fly.be.flyflix.auth.service.Impl;

import fly.be.flyflix.auth.controller.dto.LoginRequest;
import fly.be.flyflix.auth.controller.dto.LoginResponse;
import fly.be.flyflix.auth.entity.Aluno;
import fly.be.flyflix.auth.repository.AlunoRepository;
import fly.be.flyflix.auth.repository.UsuarioRepository;
import fly.be.flyflix.auth.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final AlunoRepository alunoRepository;

    public LoginServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder, AlunoRepository alunoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.alunoRepository = alunoRepository;
    }


    //Login
    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {

        var usuario = usuarioRepository.findByLogin(loginRequest.login());


        //Validar se login esta correto se o usuario existir e se a senha estiver correta
        if (usuario.isEmpty() || !usuario.get().isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("User or password is invalid!");
        }

        var aluno = alunoRepository.findByEmail(loginRequest.login());
        //Gerar e retornar token jwt com as permissoes
        var now = Instant.now();
        var expiresIn = 604800L; // expira em 1 semana
        var scopes = usuario.get().getPerfiles()
                .stream()
                .map(role -> role.getName().toUpperCase())
                .collect(Collectors.joining(" "));


        // dados do token jwt com as permissoes do usuario logado
        var claims = JwtClaimsSet.builder()
                .issuer("flyflix-backend")
                .subject(usuario.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes) // permissoes do token jwt ADMIN OU ALUNO
                .claim("allowedCategories", aluno.get().getPerfilAluno()) // categorias permitidas
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();


        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }


}
