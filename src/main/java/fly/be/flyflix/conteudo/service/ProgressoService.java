package fly.be.flyflix.conteudo.service;

import fly.be.flyflix.conteudo.entity.ProgressoAluno;
import fly.be.flyflix.conteudo.repository.ProgressoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProgressoService {

    private final ProgressoRepository progressoRepository;

    public ProgressoService(ProgressoRepository progressoRepository) {
        this.progressoRepository = progressoRepository;
    }

    public void marcarComoAssistida(Long alunoId, Long aulaId, Long cursoId) {
        Optional<ProgressoAluno> progressoExistente = progressoRepository.findByAlunoIdAndAulaId(alunoId, aulaId);

        if (progressoExistente.isPresent()) {
            ProgressoAluno progresso = progressoExistente.get();
            progresso.setAssistida(true);
            progresso.setCursoId(cursoId); // garante que o curso está atualizado
            progressoRepository.save(progresso);
        } else {
            ProgressoAluno novo = new ProgressoAluno(alunoId, aulaId, cursoId, true);
            progressoRepository.save(novo);
        }
    }
}

