package com.example.day7springboot.service;

import com.example.day7springboot.entity.Company;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 作用:
 *
 * @projectName: demo
 * @package: com.example.day7springboot.service
 * @className: CompanyService
 * @author: lhfy
 * @description: TODO
 * @date: 2025/9/9 18:29
 * @version: 1.0
 */
@Service
public class CompanyService {
    List<Company> companies = new ArrayList<>();
    public Company createCompany(Company company) {
        Company newCompany = new Company(companies.size() + 1, company.name());
        companies.add(newCompany);
        return newCompany;
    }
}
