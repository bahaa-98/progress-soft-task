package com.progress.handler;

import com.progress.service.exceptions.BadRequestException;
import com.progress.service.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class Handler {


    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Void> handleException(BadRequestException ex){
        log.error("error during processing request", ex);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Void> handleException(NotFoundException ex){
        log.error("error during processing request", ex);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
