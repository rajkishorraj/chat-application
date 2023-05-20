package com.chatlucid.service;


import com.chatlucid.entity.User;
import com.chatlucid.helper.RedisHelper;
import com.chatlucid.model.UserRegistrationOtpRequest;
import com.chatlucid.model.UserRegistrationOtpVerificationRequest;
import com.chatlucid.model.UserRegistrationResponse;
import com.chatlucid.utils.Generator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {
    private final RedisHelper redisHelper;
    private final TwilioSmsService twilioSmsService;
    private final UserService userService;

    @Value("${otp.length}")
    private Integer otpLength;

    @Value("${otp.expiration.time.in.sec}")
    private Long otpExpirationTime;

    @Override
    public UserRegistrationResponse getOtp(UserRegistrationOtpRequest userRegistrationOtpRequest) {
        String phoneNumber = userRegistrationOtpRequest.getPhoneNumber();

        String otp = Generator.generateNumber(otpLength);
        log.debug("Generated the otp");

        redisHelper.getAndSetObject(phoneNumber, otp, otpExpirationTime);

        String messageBody = "Your OTP for ChatLucid registration: " + otp;

        twilioSmsService.sendOTPMessage(phoneNumber, otp, messageBody);
        log.debug("Otp sent to number: {}", phoneNumber);

        return UserRegistrationResponse.builder()
                .message("Otp has been generated for number: " + phoneNumber)
                .build();
    }

    @Override
    public UserRegistrationResponse verifyOtp(UserRegistrationOtpVerificationRequest userRegistrationOtpVerificationRequest) {
        String phoneNumber = userRegistrationOtpVerificationRequest.getPhoneNumber();

        String otp = userRegistrationOtpVerificationRequest.getOtp();
        String expectedOtp = redisHelper.getObject(phoneNumber, String.class);

        if (!otp.equals(expectedOtp)) {
            throw new IllegalArgumentException("Otp verification failed for number: " + phoneNumber);
        }

        User user = saveUserIfNotExists(phoneNumber);

        return UserRegistrationResponse.builder()
                .message("Otp verified successfully ")
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    private User saveUserIfNotExists(String phoneNumber) {
        Optional<User> userOptional = userService.findUserByPhoneNumber(phoneNumber);
        return userOptional.orElseGet(() -> userService.saveUser(
                User.builder()
                        .phoneNumber(phoneNumber)
                        .build()
        ));
    }

}
