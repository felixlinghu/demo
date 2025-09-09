package com.example.day7springboot.controller;


import java.util.Objects;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/employees")
public class EmployeeController {

  private List<Employee> employees = new ArrayList<>();

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Employee create(@RequestBody Employee employee) {
    Employee newEmployee = new Employee(employees.size() + 1, employee.name(), employee.age(), employee.gender(), employee.salary());
    employees.add(newEmployee);
    return newEmployee;
  }
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Employee getEmployeeById(@PathVariable Integer id) {
    return employees.stream().filter(employee -> Objects.equals(employee.id(), id)).findFirst().orElse(null);
  }
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Employee> getListByGender(@RequestParam(required = false) String gender){
    return employees.stream().filter(employee -> employee.gender().equals(gender)).toList();
  }

  public void clear() {
    employees.clear();
  }
}
