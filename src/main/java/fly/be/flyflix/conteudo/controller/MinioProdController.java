package fly.be.flyflix.conteudo.controller;

import fly.be.flyflix.storage.StorageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/minio")
@Profile("prod")
public class MinioProdController {


    private final StorageService storageService;
    public MinioProdController(@Qualifier("minioService") StorageService storageService) {
        this.storageService = storageService;
    }

    // UPLOAD
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("objectName") String objectName) {
        try {
            storageService.upload(objectName, file);  // O método `upload` já é compatível com MultipartFile
            return ResponseEntity.ok("Arquivo enviado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao enviar arquivo: " + e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("objectName") String objectName) {
        try {
            storageService.delete(objectName);
            return ResponseEntity.ok("Arquivo deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar arquivo: " + e.getMessage());
        }
    }

    // GET URL
    @GetMapping("/url")
    public ResponseEntity<String> getPresignedUrl(@RequestParam("objectName") String objectName,
                                                  @RequestParam(value = "expiraMin", defaultValue = "10") int expiraMin) {
        try {
            String url = storageService.getPresignedUrl(objectName, expiraMin);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao gerar URL: " + e.getMessage());
        }
    }
}
