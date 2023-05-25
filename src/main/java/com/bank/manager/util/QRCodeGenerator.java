package com.bank.manager.util;

import com.bank.manager.model.Users;
import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Hashtable;

@Component
@Getter
public class QRCodeGenerator {
    // set up the size of the generated QR code
    private static final int WIDTH_IMAGE_QR_CODE = 150;
    private static final int HEIGHT_IMAGE_QR_CODE = 150;

    // Create a folder to save all QR code images.
    private final String STORAGE_IMAGE_QR_FOLDER = "QRCodeImageFolder";

    @Bean("qr")
    public void init() {
        File directory = new File(STORAGE_IMAGE_QR_FOLDER); // Create folder image
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public String getStorageImageQRFolder() {
        return STORAGE_IMAGE_QR_FOLDER;
    }

    public String generateQRCodeTypeBase64(Users users) {
        StringBuilder result = new StringBuilder();

        try {
            String qrCodeData = "Họ và Tên: " + users.getFullName() + "\n" +
                    "Số điện thoại: " + users.getPhoneNumber() + "\n" +
                    "Email: " + users.getEmail() + "\n" +
                    "Số tài khoản: " + users.getAccountNumber() + "\n" + "Xác thực ảnh: " + users.getActivate();

            Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();

            // allows QR codes to contain Unicode characters
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hintMap.put(EncodeHintType.MARGIN, 1);

            BitMatrix bitMatrix = new MultiFormatWriter().encode(qrCodeData, BarcodeFormat.QR_CODE,
                    WIDTH_IMAGE_QR_CODE, HEIGHT_IMAGE_QR_CODE, hintMap);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

            result.append(Base64.getEncoder().encodeToString(outputStream.toByteArray()));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return result.toString();
    }

    public void generateQRCodeImage(String base64QRCode, String phoneNumber) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64QRCode);

            ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedBytes);
            BufferedImage bufferedImage = ImageIO.read(inputStream);

            String fileName = phoneNumber + "QRcodeImage.png";   // example: 0393298480QRcodeImage.png
            Path outputFile = Paths.get(STORAGE_IMAGE_QR_FOLDER).resolve(fileName);
            ImageIO.write(bufferedImage, "png", outputFile.toFile());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}