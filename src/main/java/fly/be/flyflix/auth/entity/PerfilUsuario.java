package fly.be.flyflix.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Table(name = "perfiles")
@Entity(name = "Perfil")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerfilUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "perfil_id", nullable = true)
    private Long roleId;

    private String name;

    // enum class for const values for constants admin and basic
    public enum Values {
        ADMIN(1L),
        ALUNO(2L); //


        long roleId;

        @ManyToMany(mappedBy = "perfiles") // This is the other side of the relationship
        private Set<Usuario> usuarios = new HashSet<>();


        // constructor of enum class
        Values(long roleId) {
            this.roleId = roleId;
        }


    }

}