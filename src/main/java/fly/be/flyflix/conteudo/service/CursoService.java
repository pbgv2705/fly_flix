package fly.be.flyflix.conteudo.service;

import fly.be.flyflix.auth.repository.AlunoRepository;
import fly.be.flyflix.conteudo.repository.AulaRepository;
import fly.be.flyflix.conteudo.repository.CursoRepository;
import fly.be.flyflix.conteudo.repository.ProgressoRepository;
import fly.be.flyflix.conteudo.repository.ResultadoQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
