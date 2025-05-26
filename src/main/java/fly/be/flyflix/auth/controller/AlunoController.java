package fly.be.flyflix.auth.controller;

import fly.be.flyflix.auth.controller.dto.CadastroAluno;
import fly.be.flyflix.auth.controller.dto.aluno.AtualizarAlunoRequest;
import fly.be.flyflix.auth.entity.Aluno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fly.be.flyflix.auth.service.*;

import java.util.Map;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> cadastrar(@RequestBody CadastroAluno dados) {
        return alunoService.cadastrarAluno(dados);
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> atualizar(@RequestBody AtualizarAlunoRequest dados) {
        return alunoService.atualizarAluno(dados);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> remover(@PathVariable Long id) {
        return alunoService.removerAluno(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obter(@PathVariable Long id) {
        return alunoService.obterAluno(id);
    }

    @GetMapping
    public Page<Aluno> listar(Pageable paginacao) {
        return alunoService.listarAlunos(paginacao);
    }
}
