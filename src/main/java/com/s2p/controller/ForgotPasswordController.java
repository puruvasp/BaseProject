package com.s2p.controller;

import com.s2p.service.OtpService;
import com.s2p.util.EmailUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/auth/forgot-password")
@Tag(name = "Forgot Password APIs", description = "Endpoints for sending and verifying OTP during password reset process")
public class ForgotPasswordController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailUtility emailUtility;

    @Operation(
            summary = "Send OTP to Email",
            description = "Generates an OTP and sends it to the provided email address for password reset"
    )
    @PostMapping("/send-otp")
    public String sendOtp(@RequestBody String email) {
        String otp = otpService.generateOtp(email);
        emailUtility.sendOtpEmail(email, otp);
        return "OTP sent to " + email;
    }

    @Operation(
            summary = "Verify OTP",
            description = "Validates the OTP provided by the user against the stored OTP"
    )
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestBody String email, @RequestBody String otp) {
        boolean isValid = otpService.validateOtp(email, otp);
        return isValid ? "OTP verified" : "Invalid or expired OTP";
    }
}
