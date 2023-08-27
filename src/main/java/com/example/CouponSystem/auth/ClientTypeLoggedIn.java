package com.example.CouponSystem.auth;

import com.example.CouponSystem.enums.ClientType;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
public class ClientTypeLoggedIn {
    private ClientType clientType;
}
