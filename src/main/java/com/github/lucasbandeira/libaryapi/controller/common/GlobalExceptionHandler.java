package com.github.lucasbandeira.libaryapi.controller.common;

import com.github.lucasbandeira.libaryapi.controller.dto.ApiFieldError;
import com.github.lucasbandeira.libaryapi.controller.dto.ErrorResponse;
import com.github.lucasbandeira.libaryapi.exceptions.DuplicateRegisterException;
import com.github.lucasbandeira.libaryapi.exceptions.InvalidCampException;
import com.github.lucasbandeira.libaryapi.exceptions.OperationNotAllowedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleMethodArgumentNotValidException( MethodArgumentNotValidException e ) {
        List <FieldError> fieldErrors = e.getFieldErrors();
        List <ApiFieldError> fieldErrorList = fieldErrors.
                stream()
                .map(fieldError -> new ApiFieldError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation Error!", fieldErrorList);
    }

    @ExceptionHandler(DuplicateRegisterException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicateRegisterException( DuplicateRegisterException e ) {
        return ErrorResponse.conflict(e.getMessage());
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleOperationNotAllowedException( OperationNotAllowedException e ) {
        return ErrorResponse.standardResponse(e.getMessage());
    }


    @ExceptionHandler(InvalidCampException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleInvalidCampException( InvalidCampException e ) {
        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation Error!", List.of(new ApiFieldError(e.getField(), e.getMessage())));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)

    public ErrorResponse handleGlobalException( RuntimeException e ) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error has occurred." +
                " contact the administration", List.of());
    }
}
