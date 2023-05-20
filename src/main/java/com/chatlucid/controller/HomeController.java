package com.chatlucid.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RestController
public class HomeController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/welcome_page")
    public ResponseEntity<Map<String, String>> getWelcomeMessage() {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("Message", "Ok");
        log.debug("Called the home page");

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
}
