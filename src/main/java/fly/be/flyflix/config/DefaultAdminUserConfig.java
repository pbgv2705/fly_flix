package fly.be.flyflix.config;


import fly.be.flyflix.auth.entity.Aluno;
import fly.be.flyflix.auth.entity.PerfilAluno;
import fly.be.flyflix.auth.entity.PerfilUsuario;
import fly.be.flyflix.auth.entity.Usuario;
import fly.be.flyflix.auth.repository.AlunoRepository;
import fly.be.flyflix.auth.repository.PerfilUsuarioRepository;
import fly.be.flyflix.auth.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

@Configuration
public class DefaultAdminUserConfig implements CommandLineRunner {

    @Autowired
    private PerfilUsuarioRepository perfilUsuarioRepository;
    @Autowired
    private AlunoRepository alunoRepository;
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
        final String adminEmail = "admin@admin.com";
        final String adminCpf = "68669859432";
        var userAdmin = usuarioRepository.findByLogin(adminEmail);
        var alunoAdmin = alunoRepository.findByEmail(adminEmail);

        if (roleAdmin == null) {
            System.out.println("Role ADMIN not found in the database. ");
            return;
        }
        userAdmin.ifPresentOrElse(
                // User admin already exists
                user ->{

                    System.out.println("Admin already exist");

                },
                () -> { // User admin not exists and will be created
                    var usuario = new Usuario();
                    usuario.setLogin(adminEmail);
                    usuario.setCpf(adminCpf);
                    usuario.setSenha(passwordEncoder.encode("FlyAdmin")); // SENHA ADMINISTRADOR
                    usuario.setPerfiles(Set.of(roleAdmin));
                    usuarioRepository.save(usuario);

                    System.out.println("User admin created");
                    System.out.println(usuario);


                });

        alunoAdmin.ifPresentOrElse(
                // User Aluno admin already exists
                user ->{

                    System.out.println("Aluno admin already exist");

                },
                () -> { // User admin aluno not exists and will be created
                    var aluno = new Aluno();
                    aluno.setNome("Admin");
                    aluno.setEmail(adminEmail);
                    aluno.setCpf(adminCpf);
                    aluno.setPerfilAluno(PerfilAluno.valueOf("ALL"));
                    aluno.setAtivo(true);
                    aluno.setDataNascimento(LocalDate.of(2022, 1, 17));
                    alunoRepository.save(aluno);

                    System.out.println("User admin Aluno created");
                    System.out.println(aluno);


                });
    }
}

