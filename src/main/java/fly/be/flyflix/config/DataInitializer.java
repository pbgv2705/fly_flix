package fly.be.flyflix.config;
import fly.be.flyflix.auth.entity.Aluno;
import fly.be.flyflix.auth.repository.AlunoRepository;
import fly.be.flyflix.conteudo.entity.*;
import fly.be.flyflix.conteudo.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Profile("dev")
public class DataInitializer {

    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;
    private final ModuloRepository moduloRepository;
    private final AulaRepository aulaRepository;
    private final ProgressoRepository progressoRepository;
    private final ResultadoQuizRepository resultadoQuizRepository;

    public DataInitializer(
            AlunoRepository alunoRepository,
            CursoRepository cursoRepository,
            ModuloRepository moduloRepository1,
            AulaRepository aulaRepository,
            ProgressoRepository progressoRepository,
            ResultadoQuizRepository resultadoQuizRepository) {
        this.alunoRepository = alunoRepository;
        this.cursoRepository = cursoRepository;
        this.moduloRepository = moduloRepository1;
        this.aulaRepository = aulaRepository;
        this.progressoRepository = progressoRepository;
        this.resultadoQuizRepository = resultadoQuizRepository;
    }

    @PostConstruct
    public void init() {
        // Criar aluno
        Aluno aluno = new Aluno();
        aluno.setNome("Aluno Exemplo");
        aluno.setCpf("123.456.789-00"); // ⚠️ Definir o CPF!
        aluno.setEmail("aluno@teste.com");
        aluno.setSenha("123"); // Idealmente, use encoder
        aluno = alunoRepository.save(aluno);

        // Criar curso
        Curso curso = new Curso();
        curso.setTitulo("Curso Spring Boot");
        curso.setAutorId(99L); // ⚠️ Definir um ID válido (não pode ser null)
        curso.setDescricao("Curso de exemplo para testes no perfil dev");
        curso = cursoRepository.save(curso);

        //Criar modulo
        Modulo modulo1 = new Modulo();
        modulo1.setTitulo("Modulo 1");
        modulo1.setCurso(curso);
        modulo1.setOrdem(1);
        moduloRepository.save(modulo1);
        Modulo modulo2 = new Modulo();
        modulo2.setOrdem(2);
        modulo2.setTitulo("Modulo 2");
        modulo2.setCurso(curso);
        moduloRepository.save(modulo2);

        // Criar aulas
        Aula aula1 = new Aula();
        aula1.setTitulo("Introdução");
        aula1.setTipo(Aula.TipoConteudo.VIDEO);
        aula1.setOrdem(1);
        aula1.setModulo(modulo1);
        aula1.setCurso(curso);
        aula1 = aulaRepository.save(aula1);

        Aula aula2 = new Aula();
        aula2.setTitulo("Avançado");
        aula2.setTipo(Aula.TipoConteudo.VIDEO);
        aula2.setOrdem(2);
        aula2.setModulo(modulo2);
        aula2.setCurso(curso);
        aula2 = aulaRepository.save(aula2);

        // Progresso: marcar aulas como assistidas
        progressoRepository.saveAll(List.of(
                new ProgressoAluno(aluno.getId(), curso.getId(), aula1.getId(), true),
                new ProgressoAluno(aluno.getId(), curso.getId(), aula2.getId(), true)
        ));

        // Resultado do quiz
        ResultadoQuiz resultadoQuiz = new ResultadoQuiz();
        resultadoQuiz.setAlunoId(aluno.getId());
        resultadoQuiz.setCursoId(curso.getId());
        resultadoQuiz.setNota(8.5);
        resultadoQuiz.setData(LocalDate.now());
        resultadoQuizRepository.save(resultadoQuiz);

        System.out.println("Dados de exemplo criados com sucesso no perfil DEV.");
    }
}
