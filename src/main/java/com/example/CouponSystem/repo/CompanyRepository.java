package com.example.CouponSystem.repo;

import com.example.CouponSystem.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    boolean existsByName(String name);
    boolean existsByEmail(String email);
    Company findByEmail(String email);
    boolean existsByEmailAndPassword(String email, String password);
}
