package fly.be.flyflix.conteudo.service;

import fly.be.flyflix.conteudo.dto.modulo.AtualizacaoModulo;
import fly.be.flyflix.conteudo.dto.modulo.CadastroModulo;
import fly.be.flyflix.conteudo.dto.modulo.DetalhamentoModulo;
import fly.be.flyflix.conteudo.entity.Modulo;
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
    private ModuloRepository moduloRepository;

    @Transactional
    public Modulo cadastrar(CadastroModulo dados) {
        Modulo modulo = new Modulo();
        modulo.setTitulo(dados.titulo());
        // A ordem será definida ao associar com um curso em CursoModulo
        return moduloRepository.save(modulo);
    }

    @Transactional
    public Modulo atualizar(AtualizacaoModulo dados) {
        Modulo modulo = moduloRepository.findById(dados.id())
                .orElseThrow(() -> new EntityNotFoundException("Módulo não encontrado"));

        modulo.setTitulo(dados.titulo());
        // A ordem será atualizada no CursoModulo
        return modulo;
    }

    @Transactional
    public void remover(Long id) {
        moduloRepository.deleteById(id);
    }

    public Page<DetalhamentoModulo> listar(Pageable paginacao) {
        return moduloRepository.findAll(paginacao)
                .map(DetalhamentoModulo::new);
    }

    public DetalhamentoModulo detalhar(Long id) {
        Modulo modulo = moduloRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Módulo não encontrado"));
        return new DetalhamentoModulo(modulo);
    }
}
