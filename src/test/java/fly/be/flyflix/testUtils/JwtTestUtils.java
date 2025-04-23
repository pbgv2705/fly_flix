package fly.be.flyflix.testUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Utilitário para geração de tokens JWT para testes
 */
@TestConfiguration
@Import({fly.be.flyflix.config.SecurityConfig.class, TestSecurityConfig.class})
public class JwtTestUtils {

    @Autowired
    private JwtEncoder jwtEncoder;

    /**
     * Cria um token JWT válido com claims customizadas
     */
    private String generateToken(String subject, List<String> scopes) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("flyflix-test")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(subject)
                .claim("scope", String.join(" ", scopes))
                .id(UUID.randomUUID().toString())
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * Cria um RequestPostProcessor com token JWT para mockMvc
     */
    public RequestPostProcessor jwtRequestPostProcessor(String token) {
        return request -> {
            request.addHeader("Authorization", "Bearer " + token);
            return request;
        };
    }

    // Métodos utilitários para diferentes perfis de usuário

    public RequestPostProcessor adminUser() {
        String token = generateToken("admin@flyflix.com", Arrays.asList("SCOPE_ADMIN", "SCOPE_USER"));
        return jwtRequestPostProcessor(token);
    }

    public RequestPostProcessor normalUser() {
        String token = generateToken("user@flyflix.com", Arrays.asList("SCOPE_USER"));
        return jwtRequestPostProcessor(token);
    }

    public RequestPostProcessor customUser(String username, String... scopes) {
        String token = generateToken(username, Arrays.asList(scopes));
        return jwtRequestPostProcessor(token);
    }

    @Bean
    public JwtTestUtils jwtTestUtils() {
        return new JwtTestUtils();
    }
}