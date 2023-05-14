package com.bank.manager.controller;

import com.bank.manager.model.Users;
import com.bank.manager.service.qrcode.QRCodeService;
import com.bank.manager.service.users.UsersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class QRCodeController {
    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
    private UsersService usersService;

    @PostMapping("/generate-qr-code")
    public String generateQRCode(HttpSession session, Model model) {
        model.addAttribute("QRCodeGenerated", false);
        try {
            Users user = (Users) session.getAttribute("currentUserLogin");

            model.addAttribute("user", user);

            qrCodeService.generateQRCodeImage(usersService.findByPhoneNumber(user.getPhoneNumber()));

            model.addAttribute("QRCodeGenerated", true);
            return "form/information_users";
        } catch (Exception exception) {
            model.addAttribute("QRCodeFailed", "Create QR code failed");
            return "form/information_users";
        }
    }

    @GetMapping("/getImage/{image}")
    public ResponseEntity<Resource> getImage(@PathVariable String image) throws IOException {
        Path filePath = Paths.get("QRCodeImageFolder",image);
        byte[] imageBytes = Files.readAllBytes(filePath);

        ByteArrayResource resource = new ByteArrayResource(imageBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + image)
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(imageBytes.length)
                .body(resource);
    }
}
