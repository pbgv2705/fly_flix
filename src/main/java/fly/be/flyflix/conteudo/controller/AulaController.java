package fly.be.flyflix.conteudo.controller;

import fly.be.flyflix.conteudo.dto.aula.CadastroAula;
import fly.be.flyflix.conteudo.dto.aula.DadosAtualizacaoAula;
import fly.be.flyflix.conteudo.dto.aula.DadosDetalhamentoAula;
import fly.be.flyflix.conteudo.entity.Aula;
import fly.be.flyflix.conteudo.repository.AulaRepository;
import fly.be.flyflix.conteudo.repository.ModuloRepository;
import fly.be.flyflix.conteudo.repository.ProgressoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/aulas")
public class AulaController {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private ModuloRepository moduloRepository;

    @Autowired
    private ProgressoRepository progressoRepository;

   @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid CadastroAula dados) {
        var modulo = moduloRepository.findById(dados.moduloId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Módulo não encontrado"));

        Aula aula = new Aula();
        aula.setTitulo(dados.titulo());
        aula.setOrdem(dados.ordem());
        aula.setDuracaoEstimada(dados.duracaoEstimada());
        aula.setModulo(modulo);

        aulaRepository.save(aula);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<DadosDetalhamentoAula> listar() {
        return aulaRepository.findAll().stream()
                .map(DadosDetalhamentoAula::from)
                .toList();
    }


    @PutMapping
    @Transactional
    public ResponseEntity<?> atualizar(@RequestBody @Valid DadosAtualizacaoAula dados) {
        var aula = aulaRepository.findById(dados.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aula não encontrada"));

        var modulo = moduloRepository.findById(dados.moduloId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Módulo não encontrado"));

        aula.setTitulo(dados.titulo());
        aula.setOrdem(dados.ordem());
        aula.setDuracaoEstimada(dados.duracaoEstimada());
        aula.setModulo(modulo);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id) {
        aulaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoAula> detalhar(@PathVariable Long id) {
        var aula = aulaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aula não encontrada"));

        return ResponseEntity.ok(DadosDetalhamentoAula.from(aula));
    }


}
