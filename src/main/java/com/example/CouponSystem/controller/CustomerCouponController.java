package com.example.CouponSystem.controller;

import com.example.CouponSystem.beans.Coupon;
import com.example.CouponSystem.beans.CustomerCoupon;
import com.example.CouponSystem.exception.AuthorizationException;
import com.example.CouponSystem.exception.CouponException;
import com.example.CouponSystem.exception.CustomerCouponException;
import com.example.CouponSystem.exception.CustomerException;
import com.example.CouponSystem.service.CouponService;
import com.example.CouponSystem.service.CustomerCouponService;
import com.example.CouponSystem.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CustomerCouponController {

    private final CustomerCouponService customerCouponService;
    private final CustomerService customerService;
    private final CouponService couponService;

    @PostMapping("/company/customer_coupon/{customerId}/{couponId}")
    public CustomerCoupon addPurchase(@PathVariable int customerId, @PathVariable int couponId) throws CustomerException, CouponException, CustomerCouponException, AuthorizationException {
       return this.customerCouponService.addPurchase(this.couponService.getSingleCoupon(couponId), this.customerService.getSingleCustomer(customerId));
    }

    @DeleteMapping("/company/customer_coupon/{id}")
    public void deletePurchase(@PathVariable int id) throws CustomerCouponException {
        this.customerCouponService.deletePurchase(id);
    }
    @GetMapping("/company/customer_coupon/{id}")
    public CustomerCoupon getSinglePurchase(@PathVariable int id) throws CustomerCouponException {
        return this.customerCouponService.getSinglePurchase(id);
    }

    @GetMapping("/company/customer_coupon/byCustomer/{customerId}")
    public List<Coupon> getAllCouponsPurchasedByCustomerId(@PathVariable int customerId) throws AuthorizationException {
        return this.customerCouponService.getAllCouponsPurchasedByCustomerId(customerId);
    }
    @GetMapping("/company/customer_coupon/byCustomerAndCategory/{customerId}/{categoryId}")
    public List<Coupon> getAllCouponsByCustomerIdAndCategoryId(@PathVariable int customerId, @PathVariable int categoryId) throws AuthorizationException {
        return this.customerCouponService.getAllCouponsByCustomerIdAndCategoryId(customerId, categoryId);
    }
    @GetMapping("/company/customer_coupon/byCustomerAndMaxPrice/{customerId}/{maxPrice}")
    public List<Coupon> getAllCouponsByCustomerIdAndMaxPrice(@PathVariable int customerId, @PathVariable double maxPrice) throws AuthorizationException {
        return this.customerCouponService.getAllCouponsByCustomerIdAndMaxPrice(customerId, maxPrice);
    }

}
