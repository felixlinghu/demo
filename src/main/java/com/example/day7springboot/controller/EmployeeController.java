package com.example.day7springboot.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/employees")
public class EmployeeController {

  private List<Employee> employees = new ArrayList<>();

  @RequestMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Employee create(@RequestBody Employee employee) {
    Employee newEmployee = new Employee(employees.size() + 1, employee.name(), employee.age(), employee.gender(), employee.salary());
    employees.add(newEmployee);
    return newEmployee;
  }
}
