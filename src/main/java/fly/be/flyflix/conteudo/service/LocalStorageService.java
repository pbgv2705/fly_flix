package fly.be.flyflix.conteudo.service;

import fly.be.flyflix.storage.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Profile("dev") // Só será carregado no perfil 'dev'
@Qualifier("localStorageService") // Qualificador para a implementação local

@Slf4j
public class LocalStorageService implements StorageService {

    private final String localFolder = "uploads-dev"; // Pasta local para testes

    @Override
    public void upload(String objectName, byte[] content, String contentType) {
        File dir = new File(localFolder);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File localFile = new File(dir, objectName);
        try (FileOutputStream fos = new FileOutputStream(localFile)) {
            fos.write(content);
            log.info("Arquivo '{}' salvo localmente em '{}'", objectName, localFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo localmente", e);
        }
    }

    @Override
    public void upload(String objectName, MultipartFile file) throws Exception {
        File dir = new File(localFolder);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File localFile = new File(dir, objectName);
        try (FileOutputStream fos = new FileOutputStream(localFile)) {
            fos.write(file.getBytes());
            log.info("Arquivo '{}' salvo localmente em '{}'", objectName, localFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo localmente", e);
        }
    }

    @Override
    public void delete(String objectName) throws Exception {
        File localFile = new File(localFolder, objectName);
        if (localFile.exists()) {
            if (localFile.delete()) {
                log.info("Arquivo '{}' deletado do armazenamento local", objectName);
            } else {
                log.warn("Erro ao tentar deletar o arquivo '{}'", objectName);
            }
        } else {
            log.warn("Arquivo '{}' não encontrado para deletar", objectName);
        }
    }

    @Override
    public String getPresignedUrl(String objectName, int expirationInMinutes) {
        String fakeUrl = "http://localhost:8080/uploads-dev/" + objectName;
        log.info("URL simulada gerada: {}", fakeUrl);
        return fakeUrl;
    }
}
