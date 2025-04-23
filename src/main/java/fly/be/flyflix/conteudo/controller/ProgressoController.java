package fly.be.flyflix.conteudo.controller;

import fly.be.flyflix.conteudo.dto.ProgressoRequestDTO;
import fly.be.flyflix.conteudo.service.ProgressoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/progresso")
public class ProgressoController {

    private final ProgressoService progressoService;

    public ProgressoController(ProgressoService progressoService) {
        this.progressoService = progressoService;
    }

    @PostMapping("/assistir")
    public ResponseEntity<Void> marcarAulaComoAssistida(@RequestBody ProgressoRequestDTO dto) {
        progressoService.marcarComoAssistida(dto.getAlunoId(), dto.getAulaId(), dto.getCursoId());
        return ResponseEntity.ok().build();
    }
}

