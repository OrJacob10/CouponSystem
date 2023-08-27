package com.example.CouponSystem.service;


import com.example.CouponSystem.beans.*;
import com.example.CouponSystem.enums.ErrorMessage;
import com.example.CouponSystem.exception.AuthorizationException;
import com.example.CouponSystem.exception.CouponException;
import com.example.CouponSystem.exception.CustomerCouponException;
import com.example.CouponSystem.repo.CouponRepository;
import com.example.CouponSystem.repo.CustomerCouponRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomerCouponServiceImpl implements CustomerCouponService {

    private final CustomerCouponRepository customerCouponRepository;
    private final CustomerService customerService;
    private final CouponService couponService;
    private final CouponRepository couponRepository;
    private final ModelMapper modelMapper;


    /*
        The function receives a coupon and a customer, and adds the purchase with their id
        record in the table: customers_coupons
    */
    public CustomerCoupon addPurchase(CouponDTO couponDTO, CustomerDTO customerDTO) throws CustomerCouponException, CouponException, AuthorizationException {


        Coupon coupon = this.modelMapper.map(couponDTO, Coupon.class);
        Customer customer = this.modelMapper.map(customerDTO, Customer.class);

        if (!this.couponService.isExist(coupon) || !this.customerService.isExist(customer)) {
            throw new CustomerCouponException(ErrorMessage.ID_NOT_FOUND);
        }

        if (coupon.getAmount() < 1) {
            throw new CouponException(ErrorMessage.COUPON_AMOUNT_ZERO);
        }

        if (coupon.getEndDate().isBefore(LocalDate.now())) {
            throw new CouponException(ErrorMessage.COUPON_DATE_EXPIRED);
        }

        if (this.customerCouponRepository.existsByCouponIdAndCustomerId(coupon.getId(), customer.getId())) {
            throw new CustomerCouponException(ErrorMessage.CANT_BUY_COUPON_TWICE);
        }

        CustomerCoupon customerCoupon = CustomerCoupon.builder()
                .coupon(coupon)
                .customer(customer)
                .build();
        this.customerCouponRepository.save(customerCoupon);

        coupon.setAmount(coupon.getAmount() - 1);
        this.couponRepository.save(coupon);

        return customerCoupon;
    }

    // the function receives an id and returns the purchase by the id from the database.

    public CustomerCoupon getSinglePurchase(int id) throws CustomerCouponException {
        return this.customerCouponRepository.findById(id).orElseThrow(() -> new CustomerCouponException(ErrorMessage.ID_NOT_FOUND));
    }

    // the function receives an id and delete the purchase by the id from the database.
    public void deletePurchase(int id) throws CustomerCouponException {
        if (!this.customerCouponRepository.existsById(id)) {
            throw new CustomerCouponException(ErrorMessage.ID_NOT_FOUND);
        }
        this.customerCouponRepository.deleteById(id);
    }

    // the function receives an id of a customer and returns all the customer's coupons from the database.
    public List<Coupon> getAllCouponsPurchasedByCustomerId(int customerId) throws AuthorizationException {
        return this.customerCouponRepository.findByCustomerId(customerId).stream().map(CustomerCoupon::getCoupon).collect(Collectors.toList());
    }

    // the function receives an id of a customer and an id of a category and returns all the customer's coupons with the category id from the database.
    public List<Coupon> getAllCouponsByCustomerIdAndCategoryId(int customerId, int categoryId) throws AuthorizationException {
        return this.customerCouponRepository.findAllByCustomerIdAndCategoryId(customerId, categoryId).stream().map(CustomerCoupon::getCoupon).collect(Collectors.toList());
    }

    // the function receives an id of a customer and max price and returns all the customer's coupons with the price equal or lower from the database.
    public List<Coupon> getAllCouponsByCustomerIdAndMaxPrice(int customerId, double maxPrice) throws AuthorizationException {
        return this.customerCouponRepository.findAllByCustomerIdAndMaxPrice(customerId, maxPrice).stream().map(CustomerCoupon::getCoupon).collect(Collectors.toList());
    }
}
