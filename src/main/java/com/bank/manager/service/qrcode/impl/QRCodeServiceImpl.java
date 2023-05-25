package com.bank.manager.service.qrcode.impl;

import com.bank.manager.model.Users;
import com.bank.manager.service.qrcode.QRCodeService;
import com.bank.manager.util.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class QRCodeServiceImpl implements QRCodeService {

    private final QRCodeGenerator qrCodeGenerator;

    @Autowired
    public QRCodeServiceImpl(QRCodeGenerator qrCodeGenerator) {
        this.qrCodeGenerator = qrCodeGenerator;
    }

    @Override
    public void generateQRCodeImage(Users user) {
        String qrCodeBase64 = qrCodeGenerator.generateQRCodeTypeBase64(user);     // base 64 code
        qrCodeGenerator.generateQRCodeImage(qrCodeBase64, user.getPhoneNumber()); // image qr code
    }

    @Override
    public void deleteQRCodeImage(String phoneNumber) {
        try {
            String fileName = phoneNumber + "QRcodeImage.png";
            Path filePath = Paths.get(qrCodeGenerator.getStorageImageQRFolder()).resolve(fileName);

            Files.deleteIfExists(filePath);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
