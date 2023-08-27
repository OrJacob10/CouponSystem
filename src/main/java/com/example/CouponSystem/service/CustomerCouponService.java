package com.example.CouponSystem.service;

import com.example.CouponSystem.beans.Coupon;
import com.example.CouponSystem.beans.CouponDTO;
import com.example.CouponSystem.beans.CustomerCoupon;
import com.example.CouponSystem.beans.CustomerDTO;
import com.example.CouponSystem.exception.AuthorizationException;
import com.example.CouponSystem.exception.CouponException;
import com.example.CouponSystem.exception.CustomerCouponException;
import java.util.List;

public interface CustomerCouponService {

    CustomerCoupon addPurchase(CouponDTO couponDTO, CustomerDTO customerDTO) throws CustomerCouponException, CouponException, AuthorizationException;
    CustomerCoupon getSinglePurchase(int id) throws CustomerCouponException, CustomerCouponException;
    void deletePurchase(int id) throws CustomerCouponException;
    List<Coupon> getAllCouponsPurchasedByCustomerId(int customerId) throws AuthorizationException;
    List<Coupon> getAllCouponsByCustomerIdAndCategoryId(int customerId, int categoryId) throws AuthorizationException;
    List<Coupon> getAllCouponsByCustomerIdAndMaxPrice(int customerId, double maxPrice) throws AuthorizationException;

}
