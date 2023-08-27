package com.example.CouponSystem.exceptionAdvice;

import com.example.CouponSystem.exception.AuthorizationException;
import com.example.CouponSystem.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthorizationExceptionAdvice {
    @ExceptionHandler(value = {AuthorizationException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleError(AuthorizationException e) {
        return new ErrorResponse(e.getCode(), e.getMessage());
    }
}
