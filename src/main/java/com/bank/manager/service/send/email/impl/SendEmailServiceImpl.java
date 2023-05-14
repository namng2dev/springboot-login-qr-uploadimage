package com.bank.manager.service.send.email.impl;

import com.bank.manager.model.Users;
import com.bank.manager.service.send.email.SendEmailService;
import com.bank.manager.service.send.email.ThymeleafService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class SendEmailServiceImpl implements SendEmailService {
    @Autowired
    JavaMailSender mailSender;

    @Autowired
    ThymeleafService thymeleafService;

    @Value("${spring.mail.username}")
    private String email;

    @Override
    public void sendMailUpdatePassword(Users users) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            helper.setFrom(email);
            helper.setTo(users.getEmail());
            helper.setSubject("Thông tin tài khoản");

            Map<String, Object> variables = new HashMap<>();
            variables.put("fullName", users.getFullName());
            variables.put("phoneNumber", users.getPhoneNumber());
            variables.put("password",users.getPassword());
            variables.put("email",users.getEmail());
            variables.put("accountNumber",users.getAccountNumber());

            helper.setText(thymeleafService.createContent("information-users.html", variables), true);
            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
