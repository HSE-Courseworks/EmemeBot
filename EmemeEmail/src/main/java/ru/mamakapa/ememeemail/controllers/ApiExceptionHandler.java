package ru.mamakapa.ememeemail.controllers;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.mamakapa.ememeemail.DTOs.responses.ApiErrorResponse;
import ru.mamakapa.ememeemail.exceptions.BadRequestEmemeException;
import ru.mamakapa.ememeemail.exceptions.NotFoundEmemeException;

import java.util.Arrays;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundEmemeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotFoundException(Exception ex){
        return ApiErrorResponse.builder()
                .message(ex.getMessage())
                .stackTrace(Arrays.stream(ex.getStackTrace()).map(Object::toString).collect(Collectors.toList()))
                .build();
    }

    @ExceptionHandler({BadRequestEmemeException.class, ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleBadRequestException(Exception ex){
        return ApiErrorResponse.builder()
                .message(ex.getMessage())
                .stackTrace(Arrays.stream(ex.getStackTrace()).map(Object::toString).collect(Collectors.toList()))
                .build();
    }


}
