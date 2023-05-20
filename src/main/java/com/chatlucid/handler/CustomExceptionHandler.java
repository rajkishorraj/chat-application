package com.chatlucid.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class CustomExceptionHandler {
    private final RestResponseExceptionHandler restResponseExceptionHandler;

    public void handleException(Exception exception, HttpServletResponse httpServletResponse) throws IOException {
        ResponseEntity<?> exceptionResponse = restResponseExceptionHandler.toResponse(exception);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(exceptionResponse.getStatusCodeValue());
        PrintWriter out = httpServletResponse.getWriter();
        out.print(exceptionResponse.getBody());
        out.flush();
    }
}
