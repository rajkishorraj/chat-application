package com.chatlucid.handler;

import com.chatlucid.exception.InternalServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.chatlucid.utils.Constants.MESSAGE;
import static com.chatlucid.utils.Constants.RESPONSE_STATUS;


@Slf4j
@Component
public class RestResponseExceptionHandler {
    private static final String UNEXPECTED_ERRROR_MESSAGE = "Unexpected error occured while processing the request: ";
    private static final String PROCESSING_ERROR_MESSAGE = "Error occurred while parsing the request: ";

    public ResponseEntity<?> toResponse(Throwable exception) {
        try {
            throw exception;
        } catch (IOException ioException) {
            return getResponse(ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            return getResponse(illegalArgumentException);
        } catch (InternalServerErrorException internalServerErrorException) {
            return getResponse(internalServerErrorException);
        } catch (InterruptedException interruptedException) {
            return getResponse(interruptedException);
        } catch (Throwable throwable) {
            return getResponse(throwable);
        }
    }

    private ResponseEntity<?> getResponse(IllegalArgumentException illegalArgumentException) {
        log.error(PROCESSING_ERROR_MESSAGE, illegalArgumentException);
        return createResponse(illegalArgumentException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<?> getResponse(InternalServerErrorException internalServerErrorException) {
        log.error(PROCESSING_ERROR_MESSAGE, internalServerErrorException);
        return createResponse(internalServerErrorException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<?> getResponse(IOException ioException) {
        log.error(PROCESSING_ERROR_MESSAGE, ioException);
        return createResponse(ioException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<?> getResponse(InterruptedException interruptedException) {
        Thread.currentThread().interrupt();
        log.error(PROCESSING_ERROR_MESSAGE, interruptedException);
        return createResponse(interruptedException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<?> getResponse(Throwable throwable) {
        log.error(UNEXPECTED_ERRROR_MESSAGE, throwable);
        return createResponse(throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<?> createResponse(String message, HttpStatus httpStatus) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put(MESSAGE, message);
        responseMap.put(RESPONSE_STATUS, httpStatus.toString());
        return new ResponseEntity<>(responseMap, httpStatus);
    }

}
