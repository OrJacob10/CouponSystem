package com.example.CouponSystem.jobs;

import com.example.CouponSystem.beans.CouponDTO;
import com.example.CouponSystem.exception.AuthorizationException;
import com.example.CouponSystem.exception.CouponException;
import com.example.CouponSystem.service.CouponService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class CouponExpirationDailyJob implements Runnable{
    @Autowired
    private CouponService couponService;

    /*
       The thread goes through all the coupons in the coupon's table from the database,
       and deletes all the coupons that their end date had passed.
       The thread executes once every 24 hours
   */

    @Override
    public void run() {
        try {
            if (this.couponService.getAllCouponsExpired() != null) {
                for (CouponDTO c : this.couponService.getAllCouponsExpired()) {
                        this.couponService.deleteCoupon(c.getId());
                }
            }
            Thread.sleep(1000 * 60 * 60 * 24);
        } catch (InterruptedException | CouponException | AuthorizationException e) {
            if (e instanceof CouponException){
                System.out.println(e.getMessage());
            }
        }
    }
}
