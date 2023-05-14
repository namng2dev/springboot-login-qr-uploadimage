package com.bank.manager.controller;

import com.bank.manager.model.Users;
import com.bank.manager.service.upload.image.UploadImageService;
import com.bank.manager.service.users.UsersService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Transactional
public class UploadImageController {
    @Autowired
    private UploadImageService uploadImageService;

    @Autowired
    private UsersService usersService;

    @PostMapping("/upload/image")
    public String uploadImage(MultipartFile file, Model model, HttpSession session) {
        try {
            Users user = (Users) session.getAttribute("currentUserLogin");

            model.addAttribute("user", user);

            String fileName = uploadImageService.storeFile(file, user.getPhoneNumber());

            usersService.updateImageActivate(user.getPhoneNumber(), fileName, true);

            model.addAttribute("updateActivateSuccess","Upload Image and activate successful");
            return "form/information_users";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "form/information_users";
        }
    }
}
