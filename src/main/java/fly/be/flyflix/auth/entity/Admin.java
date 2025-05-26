package fly.be.flyflix.auth.entity;

import jakarta.persistence.Entity;
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

    @Override
    public String getRole() {
        return "ADMIN";
    }
}


