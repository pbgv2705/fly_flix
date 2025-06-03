package fly.be.flyflix.conteudo.service;

import fly.be.flyflix.conteudo.dto.aula.CadastroAula;
import fly.be.flyflix.conteudo.dto.aula.DadosAtualizacaoAula;
import fly.be.flyflix.conteudo.entity.Aula;
import fly.be.flyflix.conteudo.entity.Modulo;
import fly.be.flyflix.conteudo.repository.AulaRepository;
import fly.be.flyflix.conteudo.repository.ModuloRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private ModuloRepository moduloRepository;

    @Transactional
    public Aula cadastrar(CadastroAula dados) {
        Aula aula = new Aula();
        aula.setTitulo(dados.titulo());
        aula.setTipo(dados.tipo());
        aula.setOrdem(dados.ordem());
        aula.setDuracaoEstimada(dados.duracaoEstimada());
        aula.setLinkConteudo(dados.linkConteudo());

        // Se quiser permitir aula sem módulo, pode fazer assim:
        if (dados.moduloId() != null) {
            Modulo modulo = moduloRepository.findById(dados.moduloId())
                    .orElseThrow(() -> new EntityNotFoundException("Módulo não encontrado"));
            aula.setModulo(modulo);
        }

        return aulaRepository.save(aula);
    }

    public List<Aula> listarPorModulo(Long moduloId) {
        return aulaRepository.findByModuloId(moduloId);
    }

    @Transactional
    public Aula atualizar(Long id, DadosAtualizacaoAula dados) {
        Aula aula = aulaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aula não encontrada"));

        aula.setTitulo(dados.titulo());
        aula.setTipo(dados.tipo());
        aula.setOrdem(dados.ordem());
        aula.setDuracaoEstimada(dados.duracaoEstimada());
        aula.setLinkConteudo(dados.linkConteudo());

        if (dados.moduloId() != null) {
            Modulo modulo = moduloRepository.findById(dados.moduloId())
                    .orElseThrow(() -> new EntityNotFoundException("Módulo não encontrado"));
            aula.setModulo(modulo);
        } else {
            aula.setModulo(null); // desvincular da aula, se quiser
        }

        return aulaRepository.save(aula);
    }

    @Transactional
    public void remover(Long id) {
        aulaRepository.deleteById(id);
    }

    public Aula detalhar(Long id) {
        return aulaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aula não encontrada"));
    }
}