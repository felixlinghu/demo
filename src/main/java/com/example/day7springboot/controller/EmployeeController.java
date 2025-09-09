package com.example.day7springboot.controller;


import java.util.Objects;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
  @GetMapping("")
  @ResponseStatus(HttpStatus.OK)
  public List<Employee> getAllEmployees(){
    return employees;
  }
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
  @GetMapping(params = "gender")
  @ResponseStatus(HttpStatus.OK)
  public List<Employee> getListByGender(@RequestParam(required = true) String gender){
    return employees.stream().filter(employee -> employee.gender().equals(gender)).toList();
  }
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Employee updateEmployeeById(@PathVariable Integer id,@RequestBody Employee employee) {
    Employee employee2 = employees.stream().filter(employee1 -> Objects.equals(employee1.id(), id)).findFirst().orElse(null);
    employees.remove(employee2);
    employees.add(employee);
    return getEmployeeById(id);
  }
  @DeleteMapping ("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void updateEmployeeById(@PathVariable Integer id) {
    Employee employee2 = employees.stream().filter(employee1 -> Objects.equals(employee1.id(), id)).findFirst().orElse(null);
    employees.remove(employee2);
  }

  public void clear() {
    employees.clear();
  }
}
