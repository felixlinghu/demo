package com.example.day7springboot.controller;

import com.example.day7springboot.entity.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CompanyControllTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CompanyControll companyControll;

    @BeforeEach
    public void setup() {
        companyControll.clearCompanies();
    }

    @Test
    public void should_return_a_company_when_create_company() throws Exception {
        String requestBody = """
                        {
                  
                        "name": "alibaba"
                        }
                """;
        MockHttpServletRequestBuilder request = post("/companies").contentType(MediaType.APPLICATION_JSON).content(requestBody);
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("alibaba"));
    }

    @Test
    public void should_return_a_company_when_get_company_with_id() throws Exception {
        Company company = companyControll.createCompany(new Company(null, "alibaba"));
        String id = "/" + company.id();
        MockHttpServletRequestBuilder request = get("/companies" + id).contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(company.id()))
                .andExpect(jsonPath("$.name").value("alibaba"));
    }

    @Test
    public void should_return_all_company_when_get_company() throws Exception {
        companyControll.createCompany(new Company(null, "alibaba"));
        companyControll.createCompany(new Company(null, "tencent"));
        MockHttpServletRequestBuilder request = get("/companies").contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("alibaba"));
    }


}