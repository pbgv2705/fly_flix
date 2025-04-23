package fly.be.flyflix.conteudo.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Profile("dev")
@RestController
@RequestMapping("/local")
public class LocalStorageController {

    @GetMapping("/{filename}")
    public ResponseEntity<?> getLocalFile(@PathVariable String filename) {
        try {
            Path filePath = Path.of("uploads-dev", filename);
            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }

            String contentType = Files.probeContentType(filePath);
            byte[] content = Files.readAllBytes(filePath);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType != null ? contentType : "application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(new ByteArrayResource(content));

        } catch (Exception e) {
            log.error("Erro ao servir arquivo local", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao servir arquivo local: " + e.getMessage());
        }
    }
}

