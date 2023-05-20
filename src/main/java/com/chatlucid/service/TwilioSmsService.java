package com.chatlucid.service;

public interface TwilioSmsService {
    void sendOTPMessage(String phoneNumber, String otp, String message);
}
