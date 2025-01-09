package com.bbse.media.repository.impl;

import com.bbse.media.repository.FileSystemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Repository
public class FileSystemRepositoryImpl implements FileSystemRepository {
    @Value("${app.file.root-directory}")
    private String directory;

    @Override
    public String persistFile(byte[] content, String filename) throws IOException {
        return persistFile(content, filename, null);
    }

    @Override
    public String persistFile(byte[] content, String filename, String folderName) throws IOException {
        String uploadDir = getUploadDirectory(folderName);
        File directory = new File(uploadDir);
        checkDirectoryExists(directory);
        checkDirectoryPermissions(directory);

        Path filePath = buildFilePath(uploadDir, filename);
        Files.write(filePath, content);
        log.info("File saved: {}", filename);
        return filePath.toString();
    }

    private String getUploadDirectory(String folderName) {
        return StringUtils.hasText(folderName)
                ? directory + File.separator + folderName.trim()
                : directory;
    }

    private void checkDirectoryExists(File directory) {
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IllegalStateException(String.format("Failed to create directory %s.", directory));
        }
        log.info("Directory {} {}", directory, directory.exists() ? "already exists." : "created successfully.");
    }

    private void checkDirectoryPermissions(File directory) {
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalStateException(String.format("Directory %s is not accessible.", directory));
        }
    }

    private Path buildFilePath(String uploadDir, String filename) {
        // Validate the filename
        if (filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
            throw new IllegalArgumentException("Invalid filename");
        }
        // Normalize the path
        Path fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        return fileStorageLocation.resolve(filename);
    }

    @Override
    public void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            Files.delete(path);
            log.info("File {} deleted successfully.", filePath);
        } else {
            log.warn("File {} does not exist.", filePath);
//            throw new IllegalStateException(String.format("File %s does not exist.", filePath));
        }
    }

    @Override
    public InputStream getFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new IllegalStateException(String.format("File %s does not exist.", filePath));
        }

        return Files.newInputStream(path);
    }
}
