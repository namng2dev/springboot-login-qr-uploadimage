package com.bank.manager.service.upload.image;

import org.springframework.web.multipart.MultipartFile;

public interface UploadImageService {
    String storeFile(MultipartFile file, String phoneNumber);

    void deleteUserImage(String phoneNumber);
}
