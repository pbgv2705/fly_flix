package fly.be.flyflix.conteudo.service;

import fly.be.flyflix.auth.repository.AlunoRepository;
import fly.be.flyflix.conteudo.entity.Aula;
import fly.be.flyflix.conteudo.entity.ProgressoAluno;
import fly.be.flyflix.conteudo.entity.ResultadoQuiz;
import fly.be.flyflix.conteudo.repository.AulaRepository;
import fly.be.flyflix.conteudo.repository.CursoRepository;
import fly.be.flyflix.conteudo.repository.ProgressoRepository;
import fly.be.flyflix.conteudo.repository.ResultadoQuizRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CertificadoService {

    private final AulaRepository aulaRepository;
    private final ProgressoRepository progressoRepository;
    private final ResultadoQuizRepository resultadoQuizRepository;
    private final CursoRepository cursoRepository;
    private final AlunoRepository alunoRepository;
    private final PdfGenerator pdfGenerator;

    public CertificadoService(
            AulaRepository aulaRepository,
            ProgressoRepository progressoRepository,
            ResultadoQuizRepository resultadoQuizRepository,
            CursoRepository cursoRepository,
            AlunoRepository alunoRepository,
            PdfGenerator pdfGenerator
    ) {
        this.aulaRepository = aulaRepository;
        this.progressoRepository = progressoRepository;
        this.resultadoQuizRepository = resultadoQuizRepository;
        this.cursoRepository = cursoRepository;
        this.alunoRepository = alunoRepository;
        this.pdfGenerator = pdfGenerator;
    }

    public boolean podeEmitirCertificado(Long alunoId, Long cursoId) {
        // Buscar todas as aulas do curso
        List<Aula> aulasDoCurso = aulaRepository.findByCursoId(cursoId);

        // Buscar progresso do aluno no curso
        List<ProgressoAluno> progresso = progressoRepository.findByAlunoIdAndCursoId(alunoId, cursoId);

        // Filtrar as aulas assistidas
        Set<Long> aulasAssistidas = progresso.stream()
                .filter(ProgressoAluno::isAssistida)
                .map(ProgressoAluno::getAulaId)
                .collect(Collectors.toSet());

        // Verificar se todas as aulas foram assistidas
        boolean todasAssistidas = aulasDoCurso.stream()
                .allMatch(aula -> aulasAssistidas.contains(aula.getId()));

        // Verificar se o aluno tem nota >= 7 no quiz
        Optional<ResultadoQuiz> resultadoQuiz = resultadoQuizRepository.findByAlunoIdAndCursoId(alunoId, cursoId);
        boolean notaSuficiente = resultadoQuiz
                .map(ResultadoQuiz::getNota)
                .map(nota -> nota >= 7)
                .orElse(false);

        return todasAssistidas && notaSuficiente;
    }

    public String buscarNomeAluno(Long alunoId) {
        return alunoRepository.findById(alunoId)
                .map(aluno -> aluno.getNome())
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
    }

    public String buscarTituloCurso(Long cursoId) {
        return cursoRepository.findById(cursoId)
                .map(curso -> curso.getTitulo())
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
    }



public byte[] gerarCertificado(Long alunoId, Long cursoId) {
    Objects.requireNonNull(alunoId, "ID do aluno não pode ser nulo");
    Objects.requireNonNull(cursoId, "ID do curso não pode ser nulo");

    // MODO SIMULADO: se alunoId e cursoId forem 999, retorna certificado fake
    if (alunoId == 999L && cursoId == 999L) {
        return pdfGenerator.gerar(
                "Aluno Simulado",
                "Curso Simulado de Spring Boot",
                LocalDate.now()
        );
    }
    // Verificação real de requisitos
    if (!podeEmitirCertificado(alunoId, cursoId)) {
        throw new IllegalStateException("Aluno não qualificado para certificado");
    }

    return pdfGenerator.gerar(
            buscarNomeAluno(alunoId),
            buscarTituloCurso(cursoId),
            LocalDate.now()
    );
}
}
