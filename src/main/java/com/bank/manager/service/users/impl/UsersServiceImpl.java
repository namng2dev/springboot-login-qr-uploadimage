package com.bank.manager.service.users.impl;

import com.bank.manager.model.Users;
import com.bank.manager.repository.UsersRepository;
import com.bank.manager.response.BaseResponse;
import com.bank.manager.service.qrcode.QRCodeService;
import com.bank.manager.service.send.email.SendEmailService;
import com.bank.manager.service.upload.image.UploadImageService;
import com.bank.manager.service.users.UsersService;
import com.bank.manager.util.PasswordGenerator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SendEmailService emailService;

    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
    private UploadImageService uploadImageService;

    @Override
    public ResponseEntity<BaseResponse> createNewUsers(String fullName, String phoneNumber, String password, String email, String accountNumber) {
        /*
        In the registration form there is an account number option. If the user does not select an account number,
        the account number will be assigned as a phone number
        */
        if (usersRepository.findByPhoneNumber(phoneNumber) == null) {
            if (accountNumber == null || accountNumber.isEmpty()) {
                accountNumber = phoneNumber;
            }

            Users newUser = new Users();
            newUser.setFullName(fullName);
            newUser.setPhoneNumber(phoneNumber);
            newUser.setPassword(password);
            newUser.setEmail(email);
            newUser.setAccountNumber(accountNumber);

            /*
            Activation status: if the user hasn't uploaded a photo, this status will be false
            */

            if (newUser.getImage() == null) {
                newUser.setActivate(false);
            } else {
                newUser.setActivate(true);
            }

            usersRepository.save(newUser);
            return ResponseEntity.ok().body(new BaseResponse(true, "OK", "create users successful"));
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseResponse(false, "CONFLICT", "Registered phone number"));
    }

    @Override
    public boolean updatePassword(String fullName, String phoneNumber, String accountNumber, String email) {
        Users currentUsers = checkForgotPasswordInformation(fullName, phoneNumber, accountNumber, email);

        if (currentUsers != null) {
            currentUsers.setPassword(PasswordGenerator.generatedPassword());  // Generated a password

            emailService.sendMailUpdatePassword(currentUsers);

            usersRepository.save(currentUsers);

            return true;
        }

        return false;
    }

    /* If the user does not exist, it means it has been deleted, so there is no need to check that the User is already in the database */
    @Transactional
    @Override
    public ResponseEntity<BaseResponse> deleteUsersByPhoneNumber(Users currentUsers) {
        usersRepository.deleteByPhoneNumber(currentUsers.getPhoneNumber());      // Delete User by phone number
        qrCodeService.deleteQRCodeImage(currentUsers.getPhoneNumber());          // Delete QR code information users
        uploadImageService.deleteUserImage(currentUsers.getPhoneNumber());       // Delete image

        return ResponseEntity.ok().body(new BaseResponse(true, "OK", "delete users successful"));
    }

    @Override
    public boolean login(String phoneNumber, String password) {
        return usersRepository.findByPhoneNumberAndPassword(phoneNumber, password) != null ?
                true : false;
    }

    @Override
    public Users findByPhoneNumberAndPassword(String phoneNumber, String password) {
        return usersRepository.findByPhoneNumberAndPassword(phoneNumber, password);
    }

    @Override
    public Users findByPhoneNumber(String phoneNumber) {
        return usersRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Users checkForgotPasswordInformation(String fullName, String phoneNumber, String accountNumber, String email) {
        return usersRepository.findByFullNameAndPhoneNumberAndAccountNumberAndEmail(fullName, phoneNumber, accountNumber, email);
    }

    @Override
    public Users findAccountNumber(String accountNumber) {
        return usersRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public void updateImageActivate(String phoneNumber, String imageUrl, boolean activate) {
        Users currentUsers = findByPhoneNumber(phoneNumber);
        currentUsers.setImage(imageUrl);
        currentUsers.setActivate(activate);

        usersRepository.save(currentUsers);
    }
}
