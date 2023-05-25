package com.bank.manager.service.upload.image.impl;

import com.bank.manager.service.upload.image.UploadImageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class UploadImageServiceImpl implements UploadImageService {
    private final String STORAGE_IMAGE_FOLDER = "ImageFolder";

    @Bean("image")
    public void init() {
        File directory = new File(STORAGE_IMAGE_FOLDER); // Create folder image
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private boolean isImageFile(MultipartFile file) {
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            return bufferedImage != null;

        } catch (IOException exception) {
            throw new RuntimeException("file is not an image", exception);
        }
    }

    private float getSize(MultipartFile file) {
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

            File outputFile = new File(STORAGE_IMAGE_FOLDER + File.separator + fileName);

            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            FileCopyUtils.copy(file.getInputStream(), fileOutputStream);
            fileOutputStream.close();

            return fileName;
        } catch (IOException exception) {
            throw new RuntimeException("store file failed");
        }
    }

    @Override
    public void deleteUserImage(String phoneNumber) {
        try {
            Files.list(Paths.get(STORAGE_IMAGE_FOLDER))
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