package com.example.CouponSystem.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO{

    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
