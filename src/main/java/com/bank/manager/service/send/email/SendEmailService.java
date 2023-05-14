package com.bank.manager.service.send.email;

import com.bank.manager.model.Users;

public interface SendEmailService {
    void sendMailUpdatePassword(Users users);
}
