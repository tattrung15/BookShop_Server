package com.bookshop.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void init();

    String store(String dir, MultipartFile file, String fileName);

    Resource loadAsResource(String filename);

    void deleteFilesByPrefix(String prefix, String path);
}
