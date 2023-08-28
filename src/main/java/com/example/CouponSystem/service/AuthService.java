package com.example.CouponSystem.service;

import com.example.CouponSystem.auth.LoginRequestDTO;
import com.example.CouponSystem.auth.TokenResponseDTO;
import com.example.CouponSystem.beans.CompanyDTO;
import com.example.CouponSystem.beans.CustomerDTO;
import com.example.CouponSystem.enums.ErrorMessage;
import com.example.CouponSystem.exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private CustomerService customerService;

    // function that receives login details and returns a new token if the details are valid otherwise returns null
    public TokenResponseDTO createTokenFromLoginDetails(LoginRequestDTO loginRequestDTO) throws AuthorizationException {
        boolean isLoginDetailsValid = this.isLoginDetailsValid(loginRequestDTO);
        System.out.println(isLoginDetailsValid);
        if (isLoginDetailsValid) {
            UserDetails userDetails = this.userService.loadUserByUsername(loginRequestDTO.getEmail());
            Map<String, Object> claims = null;
            try{
                switch (loginRequestDTO.getClientType()) {
                    case ADMIN -> claims = this.adminService.buildClaims((User) userDetails);
                    case COMPANY -> claims = this.companyService.buildClaims((CompanyDTO) userDetails);
                    case CUSTOMER -> claims = this.customerService.buildClaims((CustomerDTO) userDetails);
                }
            }
            catch(Exception e){
                throw new AuthorizationException(ErrorMessage.CLIENT_TYPE_ERROR);
            }

            claims.put("clientType", loginRequestDTO.getClientType());
            String token = this.tokenService.generateToken(claims);
            long expirationTime = this.tokenService.getExpirationFromToken(token).getTime();
            return TokenResponseDTO.builder().token(token).expiration(expirationTime).build();
        }
        else {
            if (this.userService.loadUserByUsername(loginRequestDTO.getEmail()) == null){
                throw new AuthorizationException(ErrorMessage.EMAIL_NOT_FOUND);
            }
            else {
                throw new AuthorizationException(ErrorMessage.PASSWORD_NOT_FOUND);
            }
        }
    }

    // function that receives login details, if the details are valid returns true otherwise returns false.

    // authenticateManager has been initialized with a connection to the UserService class in the project
    // therefore the object automatically relates loginRequestDTO with the function loadByUserName in UserService class
    private boolean isLoginDetailsValid(LoginRequestDTO loginRequestDTO) {
            try {
                this.authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequestDTO.getEmail(),
                                loginRequestDTO.getPassword()
                        )
                );
            } catch (Exception e) {
                System.out.println("@@@@@@@@@@ CAUGHT AN ERROR @@@@@@@@@@");
                System.out.println(loginRequestDTO);
                return false;
            }
            return true;
    }
}
