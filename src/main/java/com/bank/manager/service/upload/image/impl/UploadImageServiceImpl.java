package com.bank.manager.service.upload.image.impl;

import com.bank.manager.service.upload.image.UploadImageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

@Service
public class UploadImageServiceImpl implements UploadImageService {
    private final Path storageFolder = Paths.get("ImageFolder");

    public UploadImageServiceImpl() {
        try {
            Files.createDirectories(storageFolder);

        } catch (IOException exception) {
            throw new RuntimeException("Can not initialize storage", exception);
        }
    }

    public boolean isImageFile(MultipartFile file) {
        String imageExtension = FilenameUtils.getExtension(file.getOriginalFilename());

        return Arrays.asList(new String[]{"png", "jpg", "jpeg", "bmp"}).contains(imageExtension.trim().toLowerCase());
    }

    public float getSize(MultipartFile file) {
        return file.getSize();
    }

    @Override
    public String storeFile(MultipartFile file, String phoneNumber) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("The file is empty");
            }

            if (!isImageFile(file)) {
                throw new RuntimeException("Not an image file");
            }

            if (getSize(file) / (1024 * 1024) > 2.0f) {
                throw new RuntimeException("Can't archive, because file is larger than 2 MB");
            }

            String fileName = phoneNumber + "Image." + FilenameUtils.getExtension(file.getOriginalFilename());  // example: 0393298480Image.png

            Path destinationFilePath = this.storageFolder.resolve(Paths.get(fileName)).normalize().toAbsolutePath();

            if (!Files.exists(destinationFilePath.getParent())) {
                Files.createDirectories(destinationFilePath.getParent());
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }

            return fileName;
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException("store file failed");
        }
    }

    @Override
    public void deleteUserImage(String phoneNumber) {
        try {
            Files.list(storageFolder)
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        String fileName = file.getFileName().toString();
                        if (fileName.startsWith(phoneNumber)) {
                            try {
                                Files.deleteIfExists(file);
                            } catch (IOException exception) {
                                exception.printStackTrace();
                                throw new RuntimeException("Failed to delete file: " + fileName);
                            }
                        }
                    });
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException("Failed to delete user images");
        }
    }
}