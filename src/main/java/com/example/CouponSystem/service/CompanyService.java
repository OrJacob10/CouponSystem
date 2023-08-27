package com.example.CouponSystem.service;

import com.example.CouponSystem.beans.Company;
import com.example.CouponSystem.beans.CompanyDTO;
import com.example.CouponSystem.exception.AuthorizationException;
import com.example.CouponSystem.exception.CompanyException;

import java.util.List;
import java.util.Map;

public interface CompanyService {

    CompanyDTO addCompany(CompanyDTO companyDTO) throws CompanyException, CompanyException, AuthorizationException;
    List<CompanyDTO> getCompanies() throws AuthorizationException;
    CompanyDTO getCompany(int id) throws CompanyException, AuthorizationException;
    Company getCompanyFromDB(int id) throws CompanyException;
    void updateCompany(int id, CompanyDTO companyDTO) throws CompanyException, AuthorizationException;
    void deleteCompany(int id) throws CompanyException, AuthorizationException;
    boolean isEmailAndPasswordExist(String email, String password);
    Map<String, Object> buildClaims(CompanyDTO companyDTO);
}
