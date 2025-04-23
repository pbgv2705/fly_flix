package fly.be.flyflix.conteudo.service;

import fly.be.flyflix.auth.repository.AlunoRepository;
import fly.be.flyflix.conteudo.dto.CursoResumoDTO;
import fly.be.flyflix.conteudo.entity.Curso;
import fly.be.flyflix.conteudo.repository.AulaRepository;
import fly.be.flyflix.conteudo.repository.CursoRepository;
import fly.be.flyflix.conteudo.repository.ProgressoRepository;
import fly.be.flyflix.conteudo.repository.ResultadoQuizRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private ProgressoRepository progressoRepository;

    @Autowired
    private ResultadoQuizRepository resultadoQuizRepository;

    public void incrementarVisualizacoes(Long cursoId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
        curso.setVisualizacoes(curso.getVisualizacoes() + 1);
        cursoRepository.save(curso);
    }

    // Recomendados por comportamento de usuários semelhantes
    public List<CursoResumoDTO> getCursosRecomendados(Long usuarioId) {
        List<Curso> cursos = cursoRepository.buscarRecomendados(usuarioId);
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
