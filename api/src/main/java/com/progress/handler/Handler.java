package com.progress.handler;

import com.progress.dto.response.CODE;
import com.progress.dto.response.Response;
import com.progress.service.exceptions.BadRequestException;
import com.progress.service.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class Handler {


    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Response<?>> handleException(BadRequestException ex){
        log.error("error during processing request", ex);

        Response<Object> response = Response.builder().message(ex.getMessage())
                .code(CODE.BAD_REQUEST.getId())
                .success(false)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Response<?>> handleException(NotFoundException ex){
        log.error("error during processing request", ex);

        Response<Object> response = Response.builder().message(ex.getMessage())
                .code(CODE.NOT_FOUND.getId())
                .success(false)
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
