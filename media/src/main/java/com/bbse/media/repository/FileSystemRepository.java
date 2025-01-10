package com.bbse.media.repository;

import java.io.InputStream;

public interface FileSystemRepository {
    String persistFile(byte[] content, String filename);
    String persistFile(byte[] content, String filename, String folderName);
    void deleteFile(String filePath);
    InputStream getFile(String filePath);
}
