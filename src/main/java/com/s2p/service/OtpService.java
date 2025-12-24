package com.s2p.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    private final Map<String, OtpData> otpStore = new HashMap<>();

    public String generateOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        otpStore.put(email, new OtpData(otp, LocalDateTime.now().plusMinutes(5)));
        return otp;
    }

    public boolean validateOtp(String email, String otp) {
        OtpData data = otpStore.get(email);
        if (data == null) return false;
        if (data.expiry.isBefore(LocalDateTime.now())) {
            otpStore.remove(email);
            return false;
        }
        boolean isValid = data.otp.equals(otp);
        if (isValid) otpStore.remove(email);
        return isValid;
    }

    private record OtpData(String otp, LocalDateTime expiry)
    {

    }
}
