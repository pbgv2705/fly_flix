package fly.be.flyflix.testUtils;


import fly.be.flyflix.config.SecurityConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@TestConfiguration
public class TestSecurityConfig {

    @Bean
    @Primary
    public JwtDecoder testJwtDecoder(SecurityConfig securityConfig) {
        // Usa as mesmas chaves da configuração principal para consistência
        return NimbusJwtDecoder.withPublicKey(securityConfig.getPublicKey()).build();
    }
}