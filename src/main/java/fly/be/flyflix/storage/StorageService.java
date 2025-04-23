package fly.be.flyflix.storage;
import org.springframework.web.multipart.MultipartFile;


public interface StorageService {
    void upload(String objectName, byte[] content, String contentType);

    void upload(String objectName, MultipartFile file) throws Exception;
    void delete(String objectName) throws Exception;
    String getPresignedUrl(String objectName, int expirationInMinutes) throws Exception;
}
