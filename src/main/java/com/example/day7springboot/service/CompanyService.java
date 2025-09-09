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

    public Company getCompanyById(Integer id) {
        return companies.stream().filter(company -> company.id().equals(id)).findFirst().orElse(null);
    }

    public List<Company> getAllCompanies() {
        return companies;
    }

    public void clearCompanies() {
        companies.clear();
    }

    public Company updateCompanyById(Integer id, Company company) {
        companies.stream().filter(c -> c.id().equals(id)).findFirst().ifPresent(c -> {
            companies.set(companies.indexOf(c), new Company(id, company.name()));
        });
        return getCompanyById(id);
    }

    public void deleteCompanyById(Integer id) {
        companies.removeIf(company -> company.id().equals(id));
    }

    public List<Company> getAllCompanies(Integer page, Integer size) {
        Integer pageNum = (page != null) ? page : 1;
        Integer pageSize = (size != null) ? size : 10;
        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, companies.size());
        if (fromIndex >= companies.size()) {
            return new ArrayList<>();
        }
        return companies.subList(fromIndex, toIndex);
    }
}
