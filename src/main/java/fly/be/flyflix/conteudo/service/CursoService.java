package fly.be.flyflix.conteudo.service;

import fly.be.flyflix.conteudo.dto.curso.CursoResumoDTO;
import fly.be.flyflix.conteudo.entity.Curso;
import fly.be.flyflix.conteudo.repository.CursoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public void incrementarVisualizacoes(Long cursoId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
        curso.setVisualizacoes(curso.getVisualizacoes() + 1);
        cursoRepository.save(curso);
    }

    // Recomendados por comportamento de usuários semelhantes
    public List<CursoResumoDTO> getCursosRecomendados(Long usuarioId) {
        List<Curso> cursos = cursoRepository.findCursosRecomendados(usuarioId);
        return cursos.stream().map(CursoResumoDTO::new).toList();
    }

    public List<CursoResumoDTO> getCursosNovos() {
        List<Curso> cursos = cursoRepository.findTop10ByOrderByDataPublicacaoDesc();
        return cursos.stream().map(CursoResumoDTO::new).toList();
    }

    public List<CursoResumoDTO> getCursosPopulares() {
        List<Curso> cursos = cursoRepository.findTop10ByOrderByVisualizacoesDesc();
        return cursos.stream().map(CursoResumoDTO::new).toList();
    }
}
