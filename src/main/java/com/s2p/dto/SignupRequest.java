package com.s2p.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignupRequest {

    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String phoneNumber;

}
