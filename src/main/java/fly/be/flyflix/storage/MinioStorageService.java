package fly.be.flyflix.storage;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Profile("prod")
@Service
public class MinioStorageService implements StorageService {

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;

    public MinioStorageService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public void upload(String objectName, byte[] content, String contentType) {
        try (var stream = new ByteArrayInputStream(content)) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .stream(stream, content.length, -1)
                            .contentType(contentType)
                            .build()
            );
            System.out.printf("Upload bem-sucedido: %s%n", objectName);
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao fazer upload para MinIO", e);
        }
    }

    @Override
    public void upload(String objectName, MultipartFile file) throws Exception {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            System.out.printf("Upload bem-sucedido do arquivo: %s%n", objectName);
        } catch (MinioException | IOException e) {
            throw new RuntimeException("Erro ao fazer upload do arquivo para MinIO", e);
        }
    }

    @Override
    public void delete(String objectName) throws Exception {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .build()
            );
            System.out.printf("Arquivo %s deletado com sucesso de MinIO.%n", objectName);
        } catch (MinioException e) {
            throw new RuntimeException("Erro ao deletar arquivo de MinIO", e);
        }
    }

    @Override
    public String getPresignedUrl(String objectName, int expirationInMinutes) throws Exception {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .expiry(expirationInMinutes)
                            .build()
            );
        } catch (MinioException e) {
            throw new RuntimeException("Erro ao gerar URL assinada do arquivo", e);
        }
    }
}

