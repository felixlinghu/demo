package com.example.day7springboot;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.day7springboot.controller.Employee;
import com.example.day7springboot.controller.EmployeeController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;


@SpringBootTest
@AutoConfigureMockMvc
public class EmployeesTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private EmployeeController employeeController;

  @BeforeEach
  public void setUp() {
    employeeController.clear();
  }

  @Test
  public void should_return_created_employees_when_post() throws Exception {
    //given
    String requestBody = """
                {
                "name": "John Smith",
                "age": 32,
                "gender": "Male",
                "salary": 5000.0
                }
        """;
    MockHttpServletRequestBuilder request = post("/employees").contentType(MediaType.APPLICATION_JSON).content(requestBody);
    mockMvc.perform(request)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").value("John Smith"))
        .andExpect(jsonPath("$.age").value(32))
        .andExpect(jsonPath("$.gender").value("Male"))
        .andExpect(jsonPath("$.salary").value(5000.0));
  }

  @Test
  public void should_return_employees_when_get_employees_id_correct() throws Exception {
    //given
    Employee employee = employeeController.create(new Employee(2, "John Smith", 32, "Male", 5000.0));
    String id = "/" + employee.id();
    MockHttpServletRequestBuilder request = get("/employees" + id).contentType(MediaType.APPLICATION_JSON);
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("John Smith"))
        .andExpect(jsonPath("$.age").value(32))
        .andExpect(jsonPath("$.gender").value("Male"))
        .andExpect(jsonPath("$.salary").value(5000.0));
  }

  @Test
  public void should_return_male_employees_when_get_males() throws Exception {
    //given
    Employee employee1 = employeeController.create(new Employee(3, "John Smith", 32, "Male", 5000.0));
    Employee employee2 = employeeController.create(new Employee(4, "Vega Feng", 21, "FeMale", 5000.0));
    MockHttpServletRequestBuilder request = get("/employees?gender=Male").contentType(MediaType.APPLICATION_JSON);
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].name").value("John Smith"))
        .andExpect(jsonPath("$[0].age").value(32))
        .andExpect(jsonPath("$[0].gender").value("Male"))
        .andExpect(jsonPath("$[0].salary").value(5000.0));
  }

  @Test
  public void should_return_all_employees_when_get_employees() throws Exception {
    //given
    Employee employee1 = employeeController.create(new Employee(3, "John Smith", 32, "Male", 5000.0));
    Employee employee2 = employeeController.create(new Employee(4, "Vega Feng", 21, "FeMale", 5000.0));
    MockHttpServletRequestBuilder request = get("/employees").contentType(MediaType.APPLICATION_JSON);
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].name").value("John Smith"))
        .andExpect(jsonPath("$[0].age").value(32))
        .andExpect(jsonPath("$[0].gender").value("Male"))
        .andExpect(jsonPath("$[0].salary").value(5000.0));
  }

  @Test
  public void should_return_update_employees_when_update_employees() throws Exception {
    //given
    Employee employee1 = employeeController.create(new Employee(3, "John Smith", 32, "Male", 5000.0));
    Employee employee2 = employeeController.create(new Employee(4, "Vega Feng", 21, "FeMale", 5000.0));
    String id = "/" + employee1.id();
    String requestBody = """
                {
                "id":1,
                "name": null,
                "age": 320,
                "gender": null,
                "salary": 50000.0
                }
        """;
    MockHttpServletRequestBuilder request = put("/employees" + id).contentType(MediaType.APPLICATION_JSON).content(requestBody);
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.age").value(320))
        .andExpect(jsonPath("$.salary").value(50000.0));
  }

  @Test
  public void should_return_null_when_delete_employees() throws Exception {
    //given
    Employee employee1 = employeeController.create(new Employee(3, "John Smith", 32, "Male", 5000.0));
    Employee employee2 = employeeController.create(new Employee(4, "Vega Feng", 21, "FeMale", 5000.0));
    String id = "/" + employee1.id();

    MockHttpServletRequestBuilder request = delete("/employees" + id).contentType(MediaType.APPLICATION_JSON);
    mockMvc.perform(request)
        .andExpect(status().isOk());
    assertNull(employeeController.getEmployeeById(employee1.id()));

  }

  @Test
  public void should_return_page_when_get_employees_with_page() throws Exception {
    //given
    Employee employee1 = employeeController.create(new Employee(3, "John Smith", 32, "Male", 5000.0));
    Employee employee2 = employeeController.create(new Employee(4, "Vega Feng", 21, "FeMale", 5000.0));
    Employee employee3 = employeeController.create(new Employee(4, "Vega Feng", 21, "FeMale", 5000.0));
    MockHttpServletRequestBuilder request = get("/employees?page=1&size=2").contentType(MediaType.APPLICATION_JSON);
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2));
  }


}
