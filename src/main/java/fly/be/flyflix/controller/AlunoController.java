package fly.be.flyflix.controller;

import fly.be.flyflix.domain.aluno.*;
import fly.be.flyflix.infra.exception.RecursoNaoEncontradoException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository repository;
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroAluno dados, UriComponentsBuilder uriBuilder) {
        var aluno = new Aluno(dados);
        repository.save(aluno);

        var uri = uriBuilder.path("/alunos/{cpf}").buildAndExpand(aluno.getCpf()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoAluno(aluno));
    }

    @GetMapping
    public Page<DadosDetalhamentoAluno> listar(@PageableDefault(size=10, sort = {"nome"}) Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(DadosDetalhamentoAluno::new);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoAluno dados) {
        var aluno = repository.getReferenceById(dados.id());
        aluno.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoAluno(aluno));
    }

    @DeleteMapping("/{cpf}")
    @Transactional
    public ResponseEntity remover(@PathVariable String cpf) {
        var aluno = repository.findByCpf(cpf)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno com CPF " + cpf + " não encontrado"));

        aluno.inativar();
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{cpf}")
    public ResponseEntity detalhar(@PathVariable String cpf) {
        return repository.findByCpf(cpf)
                .map(aluno -> ResponseEntity.ok(new DadosDetalhamentoAluno(aluno)))
                .orElse(ResponseEntity.notFound().build());
    }

}
