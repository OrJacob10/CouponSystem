package com.example.CouponSystem.exceptionAdvice;


import com.example.CouponSystem.exception.CustomerCouponException;
import com.example.CouponSystem.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomerCouponExceptionAdvice {

    @ExceptionHandler(value = {CustomerCouponException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleError(CustomerCouponException e) {
        return new ErrorResponse(e.getCode(), e.getMessage());
    }

}