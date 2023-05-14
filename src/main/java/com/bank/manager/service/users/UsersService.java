package com.bank.manager.service.users;

import com.bank.manager.model.Users;
import com.bank.manager.response.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface UsersService {
    ResponseEntity<BaseResponse> createNewUsers(String fullName, String phoneNumber, String password, String email, String accountNumber);

    boolean updatePassword(String fullName,String phoneNumber,String accountNumber,String email);

    ResponseEntity<BaseResponse> deleteUsersByPhoneNumber(Users currentUsers);

    boolean login(String phoneNumber, String password);

    Users findByPhoneNumberAndPassword(String phoneNumber, String password);

    Users findByPhoneNumber(String phoneNumber);

    Users checkForgotPasswordInformation(String fullName,String phoneNumber,String accountNumber,String email);

    Users findAccountNumber(String accountNumber);

    void updateImageActivate(String phoneNumber,String imageUrl,boolean activate);
}
