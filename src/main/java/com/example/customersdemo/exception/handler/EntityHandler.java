package com.example.customersdemo.exception.handler;

import com.example.customersdemo.dto.ResponseMessageDto;
import com.example.customersdemo.exception.EntityAlreadyExistsException;
import com.example.customersdemo.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class EntityHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseMessageDto> handleEntityNotFoundException(EntityNotFoundException exception) {
        return new ResponseEntity<>(new ResponseMessageDto(NOT_FOUND.value(),
                "Entity not found"), NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ResponseMessageDto> handleEntityAlreadyExistsException(EntityAlreadyExistsException exception) {
        return new ResponseEntity<>(new ResponseMessageDto(CONFLICT.value(),
                "Entity already exists"), CONFLICT);
    }
}
