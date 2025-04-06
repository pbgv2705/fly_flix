package fly.be.flyflix.auth.entity;

import jakarta.persistence.*;
import lombok.*;

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
  @Column(name = "perfil_id")
  private Long roleId;

  private String name;

    // enum class for const values for constants admin and basic
    public enum Values {
        ADMIN(1L),
        ALUNO(2L); //


        long roleId;

        // constructor of enum class
        Values(long roleId) {
            this.roleId = roleId;
        }


    }
}
