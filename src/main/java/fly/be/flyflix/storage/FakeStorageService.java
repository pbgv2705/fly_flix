package fly.be.flyflix.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Profile("dev")
@Qualifier("fakeStorageService") // Qualificador para a implementação fake
@Service
public class FakeStorageService implements StorageService {

    // Diretório onde os arquivos serão armazenados localmente
    private final Path rootLocation = Paths.get("uploads-dev");

    @Override
    public void upload(String objectName, byte[] content, String contentType) {
        try {
            // Criar diretórios, caso não existam
            Path filePath = rootLocation.resolve(objectName);
            Files.createDirectories(filePath.getParent());

            // Salvar arquivo
            Files.write(filePath, content);
            System.out.printf("Arquivo salvo localmente em %s (fake upload)%n", filePath);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer o fake upload", e);
        }
    }

    @Override
    public void upload(String objectName, MultipartFile file) throws Exception {
        try {
            // Criar diretórios, caso não existam
            Path filePath = rootLocation.resolve(objectName);
            Files.createDirectories(filePath.getParent());

            // Salvar arquivo
            file.transferTo(filePath.toFile());
            System.out.printf("Arquivo %s salvo localmente (fake upload)%n", filePath);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer o fake upload do arquivo", e);
        }
    }

    @Override
    public void delete(String objectName) throws Exception {
        try {
            Path filePath = rootLocation.resolve(objectName);
            Files.deleteIfExists(filePath);
            System.out.printf("Arquivo %s deletado localmente (fake delete)%n", filePath);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao deletar arquivo local", e);
        }
    }

    @Override
    public String getPresignedUrl(String objectName, int expirationInMinutes) throws Exception {
        // Retorna uma URL local fictícia para o arquivo
        return "http://localhost:8080/local/" + objectName;
    }
}
