package com.example.day7springboot.controller;

import com.example.day7springboot.entity.Company;
import com.example.day7springboot.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 作用:
 *
 * @projectName: demo
 * @package: com.example.day7springboot.controller
 * @className: CompanyControll
 * @author: lhfy
 * @description: TODO
 * @date: 2025/9/9 18:21
 * @version: 1.0
 */
@RestController
@RequestMapping("/companies")
public class CompanyControll {
    @Autowired
    private CompanyService companyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@RequestBody Company company) {
        return companyService.createCompany(company);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Company getCompanyById(@PathVariable Integer id) {
        return companyService.getCompanyById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public java.util.List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Company updateCompanyById(@PathVariable Integer id,@RequestBody Company company) {
        return companyService.updateCompanyById(id,company);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompanyById(@PathVariable Integer id) {
        companyService.deleteCompanyById(id);
    }

    public void clearCompanies() {
        companyService.clearCompanies();
    }
}
