package com.example.CouponSystem.service;

import com.example.CouponSystem.beans.Customer;
import com.example.CouponSystem.beans.CustomerDTO;
import com.example.CouponSystem.exception.AuthorizationException;
import com.example.CouponSystem.exception.CustomerException;
import java.util.List;

public interface CustomerService {

    CustomerDTO addCustomer(CustomerDTO customerDTO) throws CustomerException, AuthorizationException;
    List<CustomerDTO> getCustomerList() throws AuthorizationException;
    CustomerDTO getSingleCustomer(int id) throws CustomerException, AuthorizationException;
    void updateCustomer(int id, CustomerDTO customerDTO) throws CustomerException, AuthorizationException;
    void deleteCustomer(int id) throws CustomerException, AuthorizationException;
    Customer findByEmail(String email) throws CustomerException;
    boolean existsByEmailAndPassword(String email, String password);
    boolean isExist(Customer customer);
}
