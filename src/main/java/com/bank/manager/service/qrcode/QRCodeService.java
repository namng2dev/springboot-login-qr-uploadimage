package com.bank.manager.service.qrcode;

import com.bank.manager.model.Users;

public interface QRCodeService {
    void generateQRCodeImage(Users user);

    void deleteQRCodeImage(String phoneNumber);
}
