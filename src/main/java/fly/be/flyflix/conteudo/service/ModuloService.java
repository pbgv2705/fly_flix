package fly.be.flyflix.conteudo.service;
import fly.be.flyflix.conteudo.dto.modulo.AtualizacaoModulo;
import fly.be.flyflix.conteudo.dto.modulo.CadastroModulo;
import fly.be.flyflix.conteudo.dto.modulo.DetalhamentoModulo;
import fly.be.flyflix.conteudo.entity.Curso;
import fly.be.flyflix.conteudo.entity.Modulo;
import fly.be.flyflix.conteudo.repository.CursoRepository;
import fly.be.flyflix.conteudo.repository.ModuloRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModuloService {

    @Autowired
    private ModuloRepository repository;

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    public Modulo cadastrar(CadastroModulo dados) {
        Curso curso = cursoRepository.findById(dados.cursoId())
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
        Modulo modulo = new Modulo();
        modulo.setTitulo(dados.titulo());
        modulo.setOrdem(dados.ordem());
        modulo.setCurso(curso);
        return repository.save(modulo);
    }

    public Page<DetalhamentoModulo> listar(Pageable paginacao) {
        return repository.findAll(paginacao)
                .map(m -> new DetalhamentoModulo(m.getId(), m.getTitulo(), m.getOrdem(), m.getCurso().getId()));
    }

    @Transactional
    public Modulo atualizar(AtualizacaoModulo dados) {
        Modulo modulo = repository.getReferenceById(dados.id());
        modulo.setTitulo(dados.titulo());
        modulo.setOrdem(dados.ordem());
        return modulo;
    }

    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }

    public DetalhamentoModulo detalhar(Long id) {
        Modulo m = repository.findById(id).orElseThrow();
        return new DetalhamentoModulo(m.getId(), m.getTitulo(), m.getOrdem(), m.getCurso().getId());
    }
}
