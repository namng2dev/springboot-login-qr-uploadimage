package com.bank.manager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "FULL_NAME", nullable = false, length = 200)
    private String fullName;

    @Column(name = "PHONE", nullable = false, unique = true, length = 10)
    private String phoneNumber;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "ACCOUNT_NUMBER", unique = true, length = 10)  //
    private String accountNumber;

    @Column(name = "ACTIVATE_ACCOUNT")
    private Boolean activate;           //  the account has uploaded photos, the activate status will change to true

    @Column(name = "IMAGE")
    private String image;
}

