package com.bbse.media.repository;

import java.io.IOException;
import java.io.InputStream;

public interface FileSystemRepository {
    String persistFile(byte[] content, String filename) throws IOException;
    String persistFile(byte[] content, String filename, String folderName) throws IOException;
    void deleteFile(String filePath) throws IOException;
    InputStream getFile(String filePath) throws IOException;
}
