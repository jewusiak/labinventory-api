package pl.jewusiak.inwentarzeeapi.services;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@Service
public class FileStorageService {
    @Getter
    private final String rootPath = "api-data/uploads";

    @PostConstruct
    public void initialize() {
        try {
            Files.createDirectories(Path.of(rootPath));
        } catch (IOException e) {
            throw new RuntimeException("Can't initialize uploads directory: ./uploads");
        }
    }

    public UUID saveFile(MultipartFile file) {
        UUID uuid = UUID.randomUUID();
        try {
            Files.write(Path.of(rootPath + "/" + uuid), file.getBytes(), StandardOpenOption.CREATE_NEW);
        } catch (IOException e) {
            throw new RuntimeException("File already exists! " + rootPath + "/" + uuid);
        }
        return uuid;
    }

    public Resource getFile(String name) {
        try {
            Resource resource = new UrlResource(Path.of(rootPath).resolve(name).toUri());
            if (resource.exists() && resource.isReadable())
                return resource;
            else throw new RuntimeException("File can't be read!");
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void removeFile(String uuid) {
        try {
            Files.delete(Path.of(rootPath).resolve(uuid));
        } catch (IOException e) {
            throw new RuntimeException("Error removing: " + rootPath + "/" + uuid);
        }
    }
}
