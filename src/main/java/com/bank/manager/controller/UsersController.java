package com.bank.manager.controller;

import com.bank.manager.model.Users;
import com.bank.manager.service.users.UsersService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class UsersController {
    @Autowired
    private UsersService usersService;

    //Login
    @PostMapping("/login")
    public String processLogin(String phoneNumber, String password, Model model, HttpSession session) {
        if (usersService.login(phoneNumber, password)) {
            session.setAttribute("currentUserLogin", usersService.findByPhoneNumberAndPassword(phoneNumber, password));  // set information value in 1 session
            model.addAttribute("user", usersService.findByPhoneNumberAndPassword(phoneNumber, password));

            return "form/information_users";
        }
        model.addAttribute("accountNotFound", "Login failed");
        return "form/login";
    }

    @PostMapping("/create/new-users")
    public String createUsers(String fullName, String phoneNumber, String password, String email, String accountNumber, Model model) {
        if (usersService.findByPhoneNumber(phoneNumber) != null) {
            model.addAttribute("phoneNumberExist", "Register failed");

            return "form/register";

        } else if (usersService.findAccountNumber(accountNumber) != null) {
            model.addAttribute("accountNumberExist", "Register failed");

            return "form/register";

        } else {
            usersService.createNewUsers(fullName, phoneNumber, password, email, accountNumber);
            model.addAttribute("registerSuccessful", "Register successful");

            return "form/login";
        }
    }

    @PostMapping("/update/password")
    public String updatePassword(String fullName, String phoneNumber, String accountNumber, String email, Model model) {
        if (usersService.updatePassword(fullName, phoneNumber, accountNumber, email)) {
            model.addAttribute("updatePasswordSuccessful", "Update password successful");
            return "form/login";
        }

        model.addAttribute("notFoundUsers", "Users not found");
        return "form/forget_password";
    }

    @Transactional
    @PostMapping("/delete/byPhoneNumber")
    public String deleteByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber, Model model) {
        usersService.deleteUsersByPhoneNumber(usersService.findByPhoneNumber(phoneNumber));

        model.addAttribute("deleteUsersSuccessful", "Delete users successful");
        return "form/login";
    }
}
