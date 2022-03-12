package com.bookshop.services.impl;

import com.bookshop.configs.StorageProperties;
import com.bookshop.constants.Common;
import com.bookshop.exceptions.AppException;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileSystemStorageService implements StorageService {
    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(Paths.get(rootLocation + Common.PRODUCT_IMAGE_UPLOAD_PATH));
            Files.createDirectories(Paths.get(rootLocation + Common.BANNER_IMAGE_UPLOAD_PATH));
        } catch (IOException e) {
            throw new AppException("Could not initialize storage");
        }
    }

    @Override
    public String store(String dir, MultipartFile file, String fileName) {
        try {
            if (file.isEmpty()) {
                throw new AppException("Failed to store empty file");
            }
            Path pathDes = Paths.get(this.rootLocation.toString() + dir);
            Path destinationFile = pathDes.resolve(Paths.get(fileName)).normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(pathDes.toAbsolutePath())) {
                // This is a security check
                throw new AppException("Cannot store file outside current directory");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                if (dir.equals(Common.PRODUCT_IMAGE_UPLOAD_PATH)) {
                    return Common.PRODUCT_IMAGE_PATTERN_PATH + "/" + fileName;
                }
                if (dir.equals(Common.BANNER_IMAGE_UPLOAD_PATH)) {
                    return Common.BANNER_IMAGE_PATTERN_PATH + "/" + fileName;
                }
                return Common.PRODUCT_IMAGE_PATTERN_PATH + "/" + fileName;
            }
        } catch (IOException e) {
            throw new AppException("Failed to store file");
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new NotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new NotFoundException("Could not read file: " + filename);
        }
    }

    @Override
    public void deleteFilesByPrefix(String prefix, String path) {
        try {
            File file = new File(this.rootLocation.toString() + path);
            for (File item : Objects.requireNonNull(file.listFiles())) {
                if (item.getName().indexOf(prefix + "-") == 0) {
                    Files.deleteIfExists(Paths.get(item.getAbsolutePath()));
                }
            }
        } catch (IOException e) {
            throw new AppException("Failed to delete files");
        }
    }
}
