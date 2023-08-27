package com.example.CouponSystem.exception;


import com.example.CouponSystem.enums.ErrorMessage;
import lombok.Getter;

@Getter
public class CompanyException extends Exception{

    private final int code;

    public CompanyException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.code = errorMessage.getCode();
    }
}
