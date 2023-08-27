package com.example.CouponSystem.exception;

import com.example.CouponSystem.enums.ErrorMessage;
import lombok.Getter;

@Getter
public class CustomerException extends Exception{
    public final int code;

    public CustomerException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.code = errorMessage.getCode();
    }
}
