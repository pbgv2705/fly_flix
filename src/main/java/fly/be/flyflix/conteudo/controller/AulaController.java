package fly.be.flyflix.conteudo.controller;

import fly.be.flyflix.conteudo.dto.aula.CadastroAula;
import fly.be.flyflix.conteudo.dto.aula.DadosAtualizacaoAula;
import fly.be.flyflix.conteudo.dto.aula.DadosDetalhamentoAula;
import fly.be.flyflix.conteudo.entity.Aula;
import fly.be.flyflix.conteudo.repository.AulaRepository;
import fly.be.flyflix.conteudo.repository.ModuloRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@RestController
@RequestMapping("/api/aulas")
public class AulaController {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private ModuloRepository moduloRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid CadastroAula dados) {
        var modulo = moduloRepository.findById(dados.moduloId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Módulo não encontrado"));

        var aula = Aula.builder()
                .titulo(dados.titulo())
                .tipo(dados.tipo())
                .ordem(dados.ordem())
                .duracaoEstimada(dados.duracaoEstimada())
                .linkConteudo(dados.linkConteudo())
                .modulo(modulo)
                .build();

        aulaRepository.save(aula);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoAula>> listar() {
        var aulas = aulaRepository.findAll().stream().map(aula ->
                new DadosDetalhamentoAula(
                        aula.getId(),
                        aula.getTitulo(),
                        aula.getTipo(),
                        aula.getOrdem(),
                        aula.getDuracaoEstimada(),
                        aula.getLinkConteudo(),
                        aula.getModulo() != null ? aula.getModulo().getId() : null,
                        "/api/aulas/" + aula.getId() + "/capa"
                )
        ).toList();

        return ResponseEntity.ok(aulas);
    }

    @Operation(summary = "Upload da capa da aula")
    @ApiResponse(responseCode = "200", description = "Imagem salva com sucesso.")
    @PostMapping(value = "/{id}/capa", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<String> uploadCapa(
            @PathVariable Long id,
            @Parameter(description = "Imagem da capa", required = true)
            @RequestParam("imagem") MultipartFile imagem) throws Exception {

        var aula = aulaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aula não encontrada"));

        var tipo = imagem.getContentType();
        if (tipo == null || !(tipo.equals("image/jpeg") || tipo.equals("image/png"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de imagem inválido (JPEG ou PNG)");
        }

        aula.setCapa(imagem.getBytes());
        aulaRepository.save(aula);
        return ResponseEntity.ok("Imagem da capa salva com sucesso.");
    }

    @GetMapping("/{id}/capa")
    public ResponseEntity<byte[]> getCapa(@PathVariable Long id) {
        var aula = aulaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aula não encontrada"));

        if (aula.getCapa() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg") // opcional: salvar tipo MIME no banco para maior controle
                .body(aula.getCapa());
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Void> atualizar(@RequestBody @Valid DadosAtualizacaoAula dados) {
        var aula = aulaRepository.findById(dados.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aula não encontrada"));

        var modulo = moduloRepository.findById(dados.moduloId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Módulo não encontrado"));

        aula.setTitulo(dados.titulo());
        aula.setTipo(dados.tipo());
        aula.setOrdem(dados.ordem());
        aula.setDuracaoEstimada(dados.duracaoEstimada());
        aula.setLinkConteudo(dados.linkConteudo());
        aula.setModulo(modulo);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!aulaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aula não encontrada");
        }
        aulaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoAula> detalhar(@PathVariable Long id) {
        var aula = aulaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aula não encontrada"));

        var dto = new DadosDetalhamentoAula(
                aula.getId(),
                aula.getTitulo(),
                aula.getTipo(),
                aula.getOrdem(),
                aula.getDuracaoEstimada(),
                aula.getLinkConteudo(),
                aula.getModulo() != null ? aula.getModulo().getId() : null,
                "/api/aulas/" + aula.getId() + "/capa"
        );
        return ResponseEntity.ok(dto);
    }
}

