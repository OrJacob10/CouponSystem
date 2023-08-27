package com.example.CouponSystem.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponse {

    @JsonIgnore
    @ToString.Exclude
    private Exception exception;

    private int code;
    private String message;

    // Optional constructor for the first variable only
    public ErrorResponse(Exception exception) {
        this.exception = exception;
    }

    // Constructor for the code and message variables only
    public ErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
