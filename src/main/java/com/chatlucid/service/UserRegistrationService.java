package com.chatlucid.service;

import com.chatlucid.model.UserRegistrationOtpRequest;
import com.chatlucid.model.UserRegistrationOtpVerificationRequest;
import com.chatlucid.model.UserRegistrationResponse;

public interface UserRegistrationService {
    UserRegistrationResponse getOtp(UserRegistrationOtpRequest userRegistrationOtpRequest);
    UserRegistrationResponse verifyOtp(UserRegistrationOtpVerificationRequest userRegistrationOtpVerificationRequest);
}
