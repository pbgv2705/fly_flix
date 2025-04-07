package fly.be.flyflix.auth.controller;

import fly.be.flyflix.auth.controller.dto.CadastroAluno;
import fly.be.flyflix.auth.controller.dto.DadosAtualizacaoAluno;
import fly.be.flyflix.auth.controller.dto.DadosDetalhamentoAluno;
import fly.be.flyflix.auth.repository.AlunoRepository;
import fly.be.flyflix.auth.service.EmailService;
import fly.be.flyflix.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository repository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid CadastroAluno dados) {

        return userService.cadastrarAluno(dados);
    }

    @GetMapping
    public Page<DadosDetalhamentoAluno> listar(@PageableDefault(size=10, sort = {"nome"}) Pageable paginacao) {
        return userService.listar(paginacao);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoAluno dados) {

        return userService.atualizarAluno(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity remover(@PathVariable Long id) {
        return userService.removerAluno(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {

        return  userService.obterAluno(id);
    }
}
