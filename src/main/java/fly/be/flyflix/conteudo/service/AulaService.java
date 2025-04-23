package fly.be.flyflix.conteudo.service;

import fly.be.flyflix.conteudo.entity.ProgressoAluno;
import fly.be.flyflix.conteudo.repository.ProgressoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


public class AulaService {
    @Autowired
    private ProgressoRepository progressoRepository;
    public void marcarComoAssistida(Long alunoId, Long aulaId) {
        Optional<ProgressoAluno> progresso = progressoRepository.findByAlunoIdAndAulaId(alunoId, aulaId);

        if (progresso.isPresent()) {
            ProgressoAluno p = progresso.get();
            p.setAssistida(true);
            progressoRepository.save(p);
        } else {
            progressoRepository.save(new ProgressoAluno(alunoId, aulaId, true));
        }
    }


}
