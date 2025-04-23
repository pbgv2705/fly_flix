package fly.be.flyflix.conteudo.controller;


import fly.be.flyflix.conteudo.entity.Aula;
import fly.be.flyflix.conteudo.entity.Curso;
import fly.be.flyflix.conteudo.entity.Modulo;
import fly.be.flyflix.conteudo.repository.CursoRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static fly.be.flyflix.conteudo.entity.Aula.TipoConteudo.VIDEO;

@RestController
@RequestMapping("/testes")
public class TestePersistenciaController {

    private final CursoRepository cursoRepository;

    public TestePersistenciaController(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @PostMapping("/persistir-curso-completo")
    @Transactional
    public String persistirCursoCompleto() {
        // Curso
        Curso curso = new Curso();
        curso.setTitulo("Curso de Java Spring Boot");
        curso.setAutorId(1L); // <-- ESSENCIAL para passar na validação


        // Módulo 1
        Modulo modulo1 = new Modulo();
        modulo1.setTitulo("Módulo 1 - Introdução");
        modulo1.setCurso(curso);
        modulo1.setOrdem(1); // <-- Adicionei a ordem

        Aula aula1 = new Aula();
        aula1.setTitulo("Aula 1 - O que é Spring Boot");
        aula1.setLinkConteudo("https://video.link/springboot1");
        aula1.setModulo(modulo1);
        aula1.setOrdem(1); // Adicionando a ordem
        aula1.setTipo(VIDEO); // Adicionando o tipo (ajuste conforme o tipo que você utiliza)

        modulo1.setAulas(List.of(aula1));


        // Módulo 2
        Modulo modulo2 = new Modulo();
        modulo2.setTitulo("Módulo 2 - REST APIs");
        modulo2.setCurso(curso);
        modulo2.setOrdem(2); // <-- Adicionei a ordem

        Aula aula2 = new Aula();
        aula2.setTitulo("Aula 2 - Criando endpoints REST");
        aula2.setLinkConteudo("https://video.link/springboot2");
        aula2.setModulo(modulo2);
        aula2.setOrdem(2); // Adicionando a ordem
        aula2.setTipo(VIDEO); // Adicionando o tipo (ajuste conforme o tipo que você utiliza)

        modulo2.setAulas(List.of(aula2));


        // Adicionando módulos ao curso
        curso.setModulos(List.of(modulo1, modulo2));

        // Persistência em cascata
        cursoRepository.save(curso);

        return "Curso salvo com sucesso! ID: " + curso.getId();
    }
}
