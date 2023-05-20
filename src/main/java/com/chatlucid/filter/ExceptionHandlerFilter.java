package com.chatlucid.filter;

import com.chatlucid.handler.CustomExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter implements Filter {
    private final CustomExceptionHandler customExceptionHandler;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)throws ServletException, IOException {
        long startTime = System.currentTimeMillis();

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String controllerName = httpServletRequest.getRequestURI();
        String methodName = httpServletRequest.getMethod();

        try {
            chain.doFilter(servletRequest, servletResponse);
        } catch (Exception exception) {
           customExceptionHandler.handleException(exception, httpServletResponse);
        }

        long endTime = System.currentTimeMillis();
        log.info("Controller execution time: {} ms. Controller: {}. Method: {}",
                endTime - startTime, controllerName, methodName);
    }
}