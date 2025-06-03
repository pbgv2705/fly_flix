package fly.be.flyflix.conteudo.service;

import fly.be.flyflix.auth.entity.Usuario;
import fly.be.flyflix.auth.repository.UsuarioRepository;
import fly.be.flyflix.conteudo.dto.curso.AtualizacaoCurso;
import fly.be.flyflix.conteudo.dto.curso.CadastroCurso;
import fly.be.flyflix.conteudo.entity.Curso;
import fly.be.flyflix.conteudo.entity.Modulo;
import fly.be.flyflix.conteudo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModuloRepository moduloRepository;

    @Transactional
    public Curso cadastrarCurso(CadastroCurso dados) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario autor = usuarioRepository.findByNome(username)
                .orElseThrow(() -> new RuntimeException("Usuário logado não encontrado"));

        Curso curso = Curso.builder()
                .titulo(dados.titulo())
                .descricao(dados.descricao())
                .imagemCapa(dados.imagemCapa())
                .dataPublicacao(LocalDate.now())
                .autor(autor)
                .build();

        if (dados.modulosIds() != null && !dados.modulosIds().isEmpty()) {
            List<Modulo> modulos = moduloRepository.findAllById(dados.modulosIds());
            modulos.forEach(curso::adicionarModulo);
        }

        return cursoRepository.save(curso);
    }

    @Transactional
    public Curso atualizarCurso(Long cursoId, AtualizacaoCurso dados) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        if (dados.titulo() != null) curso.setTitulo(dados.titulo());
        if (dados.descricao() != null) curso.setDescricao(dados.descricao());
        if (dados.imagemCapa() != null) curso.setImagemCapa(dados.imagemCapa());

        if (dados.autorId() != null) {
            Usuario novoAutor = usuarioRepository.findById(dados.autorId())
                    .orElseThrow(() -> new RuntimeException("Autor informado não encontrado"));
            curso.setAutor(novoAutor);
        }

        return cursoRepository.save(curso);
    }
}
