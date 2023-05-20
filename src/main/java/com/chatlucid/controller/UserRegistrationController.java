package com.chatlucid.controller;

import com.chatlucid.model.UserRegistrationOtpRequest;
import com.chatlucid.model.UserRegistrationOtpVerificationRequest;
import com.chatlucid.model.UserRegistrationResponse;

import com.chatlucid.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Component
@Slf4j
@RequiredArgsConstructor
@RestController
public class UserRegistrationController {
    private final UserRegistrationService userRegistrationService;

    @GetMapping(path = "/otp/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRegistrationResponse> getOtp(
            @Valid @RequestBody UserRegistrationOtpRequest userRegistrationOtpRequest) {

        log.debug("Got the request to generate the otp for phoneNumber: {}", userRegistrationOtpRequest);
        UserRegistrationResponse userRegistrationResponse = userRegistrationService.getOtp(userRegistrationOtpRequest);
        return new ResponseEntity<>(userRegistrationResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/otp/verify", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRegistrationResponse> verifyOtp(
            @Valid @RequestBody UserRegistrationOtpVerificationRequest userRegistrationOtpVerificationRequest) {

        log.debug("Got the request to generate the otp for phoneNumber: {}", userRegistrationOtpVerificationRequest);
        UserRegistrationResponse userRegistrationResponse = userRegistrationService.verifyOtp(userRegistrationOtpVerificationRequest);
        return new ResponseEntity<>(userRegistrationResponse, HttpStatus.OK);
    }

}
