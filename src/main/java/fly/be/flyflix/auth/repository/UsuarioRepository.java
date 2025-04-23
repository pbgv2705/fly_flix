package fly.be.flyflix.auth.repository;

import fly.be.flyflix.auth.entity.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @EntityGraph(attributePaths = "perfiles")
    Optional<Usuario> findByLogin(String login);

    Optional<Usuario> findByCpf(String cpf);
}
