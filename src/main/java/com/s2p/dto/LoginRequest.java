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
public class LoginRequest {

    @Schema(description = "User email", required = true)
    private String email;

    @Schema(description = "User password", required = true)
    private String password;

}
