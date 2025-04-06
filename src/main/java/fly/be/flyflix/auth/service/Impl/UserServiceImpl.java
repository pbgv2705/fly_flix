package fly.be.flyflix.auth.service.Impl;

import fly.be.flyflix.auth.entity.Aluno;
import fly.be.flyflix.auth.controller.dto.CadastroAlunoDTO;
import fly.be.flyflix.auth.entity.PerfilUsuario;
import fly.be.flyflix.auth.entity.Usuario;
import fly.be.flyflix.auth.repository.AlunoRepository;
import fly.be.flyflix.auth.repository.PerfilUsuarioRepository;
import fly.be.flyflix.auth.repository.UsuarioRepository;
import fly.be.flyflix.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {


    private final UsuarioRepository usuarioRepository;
    private final AlunoRepository alunoRepository;
    private final PasswordEncoder passwordEncoder;
    private final PerfilUsuarioRepository perfilUsuarioRepository;

    public UserServiceImpl(UsuarioRepository usuarioRepository, AlunoRepository alunoRepository, PasswordEncoder passwordEncoder, PerfilUsuarioRepository perfilUsuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.alunoRepository = alunoRepository;
        this.passwordEncoder = passwordEncoder;
        this.perfilUsuarioRepository = perfilUsuarioRepository;
    }



    //CadastrarUsuario
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Override
    public ResponseEntity<Map<String, Object>> cadastrarUsuario(CadastroAlunoDTO dados) {

        //validar se o email ja esta cadastrado no banco
        var usuarioEmailDB = usuarioRepository.findByLogin(dados.email());
        if (usuarioEmailDB.isPresent()) {

            Map<String, Object> response = new HashMap<>();
            response.put("campo", "email");
            response.put("message", "Ja tem um cadastro com esse email");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        //validar se o cpf ja esta cadastrado no banco
        var usuarioCpfDB = usuarioRepository.findByCpf(dados.cpf());
        if (usuarioCpfDB.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("campo", "cpf");
            response.put("message", "Ja tem um cadastro com esse CPF");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        //Criando objeto com os dados do usuario
        var usuarioPerfil = perfilUsuarioRepository.findByName(PerfilUsuario.Values.ALUNO.name());

        // Verificar se o perfil foi encontrado
        if (usuarioPerfil == null) {
            // Lançar uma exceção ou retornar uma resposta de erro, dependendo da sua lógica
            throw new IllegalStateException("Perfil 'ALUNO' não encontrado.");
        }

        Usuario usuario = Usuario.builder()
                .cpf(dados.cpf())
                .login(dados.email())
                .senha(passwordEncoder.encode(dados.senha()))
                .perfiles(Set.of(usuarioPerfil))  // Asignando Rol Aluno
                .build();
        usuarioRepository.save(usuario);


        //Criando objeto com os dados do aluno

        Aluno aluno = Aluno.builder()
                    .cpf(dados.cpf())
                    .email(dados.email())
                    .usuario(usuario)
                    .nome(dados.nome())
                    .dataNascimento(dados.dataNascimento())
                    .ativo(true)
                    .build();

        alunoRepository.save(aluno);

        return ResponseEntity.ok().build();
    }
}
