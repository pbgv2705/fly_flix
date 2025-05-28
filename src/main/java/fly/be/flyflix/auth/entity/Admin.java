package fly.be.flyflix.auth.entity;

import fly.be.flyflix.auth.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends Usuario {

    private Boolean ativo;

    // Remova getRole() - herdará de Usuario

    // Se quiser garantir que role seja ADMIN, faça um método de inicialização:
    @PrePersist
    public void prePersist() {
        setRole(Role.ADMIN);
    }
}


