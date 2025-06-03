package fly.be.flyflix.conteudo.controller;

import fly.be.flyflix.conteudo.dto.curso.AtualizacaoCurso;
import fly.be.flyflix.conteudo.dto.curso.CadastroCurso;
import fly.be.flyflix.conteudo.dto.curso.CursoResumoDTO;
import fly.be.flyflix.conteudo.dto.curso.DetalhamentoCurso;
import fly.be.flyflix.conteudo.entity.Curso;
import fly.be.flyflix.conteudo.repository.CursoRepository;
import fly.be.flyflix.conteudo.service.CursoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoRepository repository;

    @Autowired
    private CursoService cursoService;

    @PostMapping
    @Transactional
    public ResponseEntity<DetalhamentoCurso> cadastrar(@RequestBody @Valid CadastroCurso dados) {
        try {
            Curso curso = cursoService.cadastrarCurso(dados);
            return ResponseEntity
                    .created(URI.create("/api/cursos/" + curso.getId()))
                    .body(new DetalhamentoCurso(curso));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(null); // ou .build() se preferir sem corpo
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping
    public Page<DetalhamentoCurso> listar(@PageableDefault(size = 10, sort = "titulo") Pageable paginacao) {
        return repository.findAll(paginacao).map(DetalhamentoCurso::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhamentoCurso> detalhar(@PathVariable Long id) {
        Optional<Curso> optional = repository.findById(id);
        return optional.map(curso -> ResponseEntity.ok(new DetalhamentoCurso(curso)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DetalhamentoCurso> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoCurso dados) {
        Optional<Curso> optional = repository.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();

        Curso curso = optional.get();
        curso.setTitulo(dados.titulo());
        curso.setDescricao(dados.descricao());
        curso.setImagemCapa(dados.imagemCapa());


        try {

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new DetalhamentoCurso(curso));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
