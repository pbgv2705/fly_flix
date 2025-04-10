package fly.be.flyflix.config;


import fly.be.flyflix.auth.entity.PerfilUsuario;
import fly.be.flyflix.auth.entity.Usuario;
import fly.be.flyflix.auth.repository.PerfilUsuarioRepository;
import fly.be.flyflix.auth.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Configuration
public class DefaultAdminUserConfig implements CommandLineRunner {

    @Autowired
    private PerfilUsuarioRepository perfilUsuarioRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    //Criar usuario admin quando rodar aplicação
    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var roleAdmin = perfilUsuarioRepository.findByName(PerfilUsuario.Values.ADMIN.name());
        System.out.println("******************************************************************************************" );
        System.out.println("RoleAdmin: " + roleAdmin);
        System.out.println("******************************************************************************************" );
        var userAdmin = usuarioRepository.findByLogin("admin@admin.com");

        if (roleAdmin == null) {
            System.out.println("Role ADMIN not found in the database. Aborting user creation.");
            return;
        }

        userAdmin.ifPresentOrElse(
                // User admin already exists
                user ->{

                    System.out.println("Creating admin user");

                },
                () -> { // User admin not exists and will be created
                    var usuario = new Usuario();
                    usuario.setLogin("admin@admin.com");
                    usuario.setCpf("68669859432");
                    usuario.setSenha(passwordEncoder.encode("FlyAdmin"));
                    usuario.setPerfiles(Set.of(roleAdmin));
                    usuarioRepository.save(usuario);

                    System.out.println("User admin created");
                    System.out.println(usuario);


                });
    }
}

