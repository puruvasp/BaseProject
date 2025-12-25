package com.s2p.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignupRequest {

    @Schema(description = "User name", required = true)
    private String username;

    @Schema(description = "User email", required =true)
    private String email;

    @Schema(description = "User password", required = true)
    private String password;

    @Schema(description = "User confirm password", required = true)
    private String confirmPassword;

    @Schema(description = "User phone number", required = true)
    private String phoneNumber;

}
