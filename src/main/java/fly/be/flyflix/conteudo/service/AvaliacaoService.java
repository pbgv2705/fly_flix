package fly.be.flyflix.conteudo.service;

import fly.be.flyflix.conteudo.entity.Avaliacao;
import fly.be.flyflix.conteudo.entity.Curso;
import fly.be.flyflix.conteudo.repository.AvaliacaoRepository;
import fly.be.flyflix.conteudo.repository.CursoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    public void avaliarCurso(Long cursoId, int nota, Long usuarioId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setNota(nota);
        avaliacao.setCurso(curso);
        avaliacao.setUsuarioId(usuarioId);

        avaliacaoRepository.save(avaliacao);

        // Atualiza média e contagem
        List<Avaliacao> avaliacoes = avaliacaoRepository.findByCursoId(cursoId);
        double media = avaliacoes.stream().mapToInt(Avaliacao::getNota).average().orElse(0.0);
        curso.setMediaAvaliacoes(media);
        curso.setTotalAvaliacoes(avaliacoes.size());

        cursoRepository.save(curso);
    }
}
