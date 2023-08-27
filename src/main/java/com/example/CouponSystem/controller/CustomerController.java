package com.example.CouponSystem.controller;

import com.example.CouponSystem.beans.CustomerDTO;
import com.example.CouponSystem.exception.AuthorizationException;
import com.example.CouponSystem.exception.CustomerException;
import com.example.CouponSystem.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/admin/customer")
    public CustomerDTO addCustomer(@RequestBody CustomerDTO customerDTO) throws CustomerException, AuthorizationException {
        return this.customerService.addCustomer(customerDTO);
    }

    @PutMapping("/admin/customer/{id}")
    public void updateCustomer(@PathVariable int id, @RequestBody CustomerDTO customerDTO) throws CustomerException, AuthorizationException {
        this.customerService.updateCustomer(id, customerDTO);
    }

    @GetMapping("/admin/customer/{id}")
    public CustomerDTO getSingleCustomer(@PathVariable int id) throws CustomerException, AuthorizationException {
        return this.customerService.getSingleCustomer(id);
    }

    @GetMapping("/admin/customer")
    public List<CustomerDTO> getCustomers() throws AuthorizationException {
        return this.customerService.getCustomerList();
    }

    @DeleteMapping("/admin/customer/{id}")
    public void deleteCustomer(@PathVariable int id) throws CustomerException, AuthorizationException {
        this.customerService.deleteCustomer(id);
    }
}
