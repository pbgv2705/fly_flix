package fly.be.flyflix.controller;

import fly.be.flyflix.domain.conteudo.Conteudo;
import fly.be.flyflix.domain.conteudo.ConteudoRepository;
import fly.be.flyflix.domain.conteudo.DadosConteudo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("conteudo")
public class ConteudoController {
    @Autowired
    private ConteudoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosConteudo dados, UriComponentsBuilder uriBuilder) {
        var conteudo = new Conteudo(dados);
        repository.save(conteudo);

        var uri = uriBuilder.path("/conteudo/{id}").buildAndExpand(conteudo.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosConteudo(conteudo));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosConteudo dados) {
        var conteudo = repository.getReferenceById(dados.id());
        conteudo.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosConteudo(conteudo));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity remover(@PathVariable Long id) {
        var conteudo = repository.getReferenceById(id);
        conteudo.arquivar();
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var conteudo = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosConteudo(conteudo));
    }
}
