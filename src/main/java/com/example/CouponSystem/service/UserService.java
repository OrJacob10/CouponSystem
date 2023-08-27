package com.example.CouponSystem.service;

import com.example.CouponSystem.beans.Company;
import com.example.CouponSystem.beans.CompanyDTO;
import com.example.CouponSystem.beans.Customer;
import com.example.CouponSystem.beans.CustomerDTO;
import com.example.CouponSystem.enums.ErrorMessage;
import com.example.CouponSystem.exception.AuthorizationException;
import com.example.CouponSystem.repo.UserRepository;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    private final CompanyService companyService;
    private final CustomerService customerService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(CompanyService companyService,
                       TokenService tokenService,
                       @Lazy PasswordEncoder passwordEncoder,
                       CustomerService customerService, UserRepository userRepository, ModelMapper modelMapper) {
        this.companyService = companyService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.customerService = customerService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @SneakyThrows
    @Override
    // the function's parameter is an email
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals("admin@admin.com")) {
            return new User("admin@admin.com", this.passwordEncoder.encode("admin"), new ArrayList<>());
        }
        Company company = (Company) this.companyService.findByEmail(username);
        if (company != null) {
            return this.modelMapper.map(company, CompanyDTO.class);
        }

        Customer customer = this.customerService.findByEmail(username);
        if (customer!= null){
            return this.modelMapper.map(customer, CustomerDTO.class);
        }
        else throw new AuthorizationException(ErrorMessage.EMAIL_NOT_FOUND);
    }
}
