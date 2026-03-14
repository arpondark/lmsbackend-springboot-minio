package site.shazan.lmsbackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

@Service
public class FileStorageService {
    private static final Set<String> IMAGE_TYPES = Set.of("image/jpeg", "image/png", "image/webp", "image/gif");
    private static final Set<String> VIDEO_TYPES = Set.of("video/mp4", "video/webm", "video/ogg", "video/quicktime");

    private final Path uploadRoot;

    public FileStorageService(@Value("${app.upload.root:uploads}") String uploadRootPath) {
        this.uploadRoot = Paths.get(uploadRootPath).toAbsolutePath().normalize();
    }

    public String storeImage(MultipartFile file, String folder) {
        validateFile(file, IMAGE_TYPES, "image");
        return store(file, folder);
    }

    public String storeVideo(MultipartFile file, String folder) {
        validateFile(file, VIDEO_TYPES, "video");
        return store(file, folder);
    }

    private void validateFile(MultipartFile file, Set<String> allowedTypes, String label) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Please provide a " + label + " file");
        }

        String contentType = file.getContentType();
        if (contentType == null || !allowedTypes.contains(contentType.toLowerCase())) {
            throw new IllegalArgumentException("Unsupported " + label + " content type: " + contentType);
        }
    }

    private String store(MultipartFile file, String folder) {
        try {
            Path targetDir = uploadRoot.resolve(folder).normalize();
            Files.createDirectories(targetDir);

            String original = file.getOriginalFilename() == null ? "file" : file.getOriginalFilename();
            String extension = getExtension(original);
            String fileName = UUID.randomUUID() + extension;
            Path target = targetDir.resolve(fileName).normalize();

            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/" + folder + "/" + fileName;
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to store file", ex);
        }
    }

    private String getExtension(String fileName) {
        int idx = fileName.lastIndexOf('.');
        return idx >= 0 ? fileName.substring(idx) : "";
    }
}

