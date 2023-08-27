package com.example.CouponSystem.repo;

import com.example.CouponSystem.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByEmailAndPassword(String email, String password);
}
