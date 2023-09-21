package com.example.CouponSystem.jobs;

import com.example.CouponSystem.beans.CouponDTO;
import com.example.CouponSystem.exception.AuthorizationException;
import com.example.CouponSystem.exception.CouponException;
import com.example.CouponSystem.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class CouponExpirationDailyJob{
    @Autowired
    private CouponService couponService;

    /*
       The thread goes through all the coupons in the coupon's table from the database,
       and deletes all the coupons that their end date had passed.
       The thread executes once every 24 hours
   */
    @Async
    @Scheduled(cron = "0 0 0 * * ?")
    public void expiredCouponJob() {
         // Run at midnight every day
            try {
                List<CouponDTO> expiredCoupons = couponService.getAllCouponsExpired();
                for (CouponDTO coupon : expiredCoupons) {
                    couponService.deleteCoupon(coupon.getId());
                }
            } catch (CouponException | AuthorizationException e) {
                if (e instanceof CouponException){
                    System.out.println(e.getMessage());
                }
        }
    }
}
