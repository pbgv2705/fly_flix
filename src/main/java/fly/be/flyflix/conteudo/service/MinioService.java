package fly.be.flyflix.conteudo.service;

import fly.be.flyflix.storage.StorageService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;


@Profile("prod")
@Service
public class MinioService implements StorageService {
    private final MinioClient minioClient;


    @Value("${minio.bucket}")
    private String bucket;

    public MinioService(@Qualifier("minioClient") MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public void upload(String objectName, byte[] content, String contentType) {
        try (InputStream is = new java.io.ByteArrayInputStream(content)) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .stream(is, content.length, -1)
                            .contentType(contentType)
                            .build()
            );
            System.out.printf("Arquivo '%s' carregado para o MinIO com sucesso.%n", objectName);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer upload do arquivo para o MinIO", e);
        }
    }

    @Override
    public void upload(String objectName, MultipartFile file) throws Exception {
        try (InputStream is = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .stream(is, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            System.out.printf("Arquivo '%s' carregado para o MinIO com sucesso.%n", objectName);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer upload do arquivo para o MinIO", e);
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
            System.out.printf("Arquivo '%s' deletado do MinIO com sucesso.%n", objectName);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar o arquivo do MinIO", e);
        }
    }

    @Override
    public String getPresignedUrl(String objectName, int expirationInMinutes) throws Exception {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucket)
                            .object(objectName)
                            .expiry(expirationInMinutes, TimeUnit.MINUTES)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar URL pré-assinada para o MinIO", e);
        }
    }
}
