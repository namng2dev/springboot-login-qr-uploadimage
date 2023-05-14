package com.bank.manager.repository;

import com.bank.manager.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    Users findByPhoneNumber(String phoneNumber);

    Users findByPhoneNumberAndPassword(String phoneNumber,String password);

    Users findByFullNameAndPhoneNumberAndAccountNumberAndEmail(String fullName,String phoneNumber, String accountNumber,String email);

    void deleteByPhoneNumber(String phoneNumber);

    Users findByAccountNumber(String accountNumber);
}
