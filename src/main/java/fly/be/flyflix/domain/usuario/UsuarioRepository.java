
package fly.be.flyflix.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByLogin(String login); // se Usuario implementa UserDetails
    Usuario findByCpf(String cpf);     // busca por CPF
}
