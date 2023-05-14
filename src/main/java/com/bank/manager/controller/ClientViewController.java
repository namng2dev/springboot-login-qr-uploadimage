package com.bank.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientViewController {
    @GetMapping("/")
    public String loginForm() {
        return "form/login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "form/register";
    }

    @GetMapping("/forget-password")
    public String forgetPasswordForm() {
        return "form/forget_password";
    }

    @GetMapping("/information-users")
    public String informationUsers() {
        return "form/information_users";
    }

    @GetMapping("/upload-image")
    public String uploadImage() {
        return "form/upload_image";
    }
}
