package com.example.CouponSystem.validations;

import com.example.CouponSystem.beans.CompanyDTO;
import com.example.CouponSystem.beans.CustomerDTO;
import com.example.CouponSystem.enums.ErrorMessage;
import com.example.CouponSystem.exception.AuthorizationException;
import com.example.CouponSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class ValidateClient {

    @Autowired
    private UserService userService;

    // function that validates if the user is a company
    public void validateUserIsCompany() throws AuthorizationException {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (!(userService.loadUserByUsername(userDetails.getUsername()) instanceof CompanyDTO)) {
                throw new AuthorizationException(ErrorMessage.NOT_AUTHORIZED);
            }
        }
    }
    // function that validates if the user is a company by company Id
    public void validateUserIsCompany(int companyId) throws AuthorizationException {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (!(userService.loadUserByUsername(userDetails.getUsername()) instanceof CompanyDTO)) {
                throw new AuthorizationException(ErrorMessage.NOT_AUTHORIZED);
            }

            if (((CompanyDTO) userService.loadUserByUsername(userDetails.getUsername())).getId() != companyId) {
                throw new AuthorizationException(ErrorMessage.NOT_AUTHORIZED);
            }
        }
    }

    // function that validates if the user is a customer
    public void validateUserIsCustomer() throws AuthorizationException{
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (!(userService.loadUserByUsername(userDetails.getUsername()) instanceof CustomerDTO)) {
                throw new AuthorizationException(ErrorMessage.NOT_AUTHORIZED);
            }
        }
    }
    // function that validates if the user is a company by customer Id
    public void validateUserIsCustomer(int customerId) throws AuthorizationException{
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (!(userService.loadUserByUsername(userDetails.getUsername()) instanceof CustomerDTO)) {
                throw new AuthorizationException(ErrorMessage.NOT_AUTHORIZED);
            }

            if (((CustomerDTO) userService.loadUserByUsername(userDetails.getUsername())).getId() != customerId) {
                throw new AuthorizationException(ErrorMessage.NOT_AUTHORIZED);
            }
        }
    }

    // function that validates if the user is an admin
    public void validateUserIsAdmin() throws AuthorizationException{
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (userService.loadUserByUsername(userDetails.getUsername()) instanceof CustomerDTO || userService.loadUserByUsername(userDetails.getUsername()) instanceof CompanyDTO) {
                throw new AuthorizationException(ErrorMessage.NOT_AUTHORIZED);
            }
        }
    }
}
