/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.gt.interpackage.dto.ChangePasswordDTO;
import com.gt.interpackage.service.EmployeeTypeService;
import com.gt.interpackage.service.EmployeeService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.gt.interpackage.model.Employee;
import com.gt.interpackage.source.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 *
 * @author Bryan y Luis
 */
@WebMvcTest (EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private EmployeeService employeeService;
    
    @MockBean 
    private EmployeeTypeService employeeTypeService;
    
    private Employee employeeMail;
    private Employee employee;
    private ObjectMapper objectMapper;
    private ChangePasswordDTO dto;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.employeeMail = new Employee("Juan", "Gonzales", 1, "12345678", "juan123@gmail.com", "juan123", true);
        this.employee = new Employee(12355L, "Jose Manuel", "Garcia", 1, "12345678", "prueba@gmail.com", "josema12", true);
        this.objectMapper = new ObjectMapper();
        this.dto = new ChangePasswordDTO("12345678", "12345678", "as3d21asd2f1as3d2f1a3sd21fasd32f");
    }
    
    @Test
    public void testGetUserByEmail() throws Exception {
        System.out.println("EmployeeControllerTest - getUserByEmail");
        Mockito
                .when(employeeService.getUserByUsernameOrEmail(ArgumentMatchers.any(String.class)))
                .thenReturn(employeeMail);
        mockMvc.perform(MockMvcRequestBuilders.get(Constants.API_V1 + "/employee/search-by-email/juan123@gmail.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("juan123@gmail.com"))
                .andExpect(MockMvcResultMatchers.content().json(this.objectMapper.writeValueAsString(employeeMail)));
        Mockito.verify(employeeService).getUserByUsernameOrEmail(ArgumentMatchers.any(String.class));
    }
    
    @Test
    public void testChangePassword() throws Exception {
        System.out.println("EmployeeControllerTest - changePassword");
        Mockito
                .when(employeeService.getUserByTokenPassword(ArgumentMatchers.any(String.class)))
                .thenReturn(employeeMail);
        Mockito
                .when(employeeService.save(ArgumentMatchers.any(Employee.class)))
                .thenReturn(employeeMail);
        mockMvc.perform(MockMvcRequestBuilders.post(Constants.API_V1 + "/employee/change-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Contraseña actualizada con exito."));
        Mockito.verify(employeeService).getUserByTokenPassword(ArgumentMatchers.any(String.class));
        Mockito.verify(employeeService).save(ArgumentMatchers.any(Employee.class));
    }
    
    @Test
    public void testGetAllEmployees() throws Exception {
        System.out.println("EmployeeControllerTest - findAllActivates");
        Mockito.when(employeeService.findAllActivates()).thenReturn(Arrays.asList(employee));
        mockMvc.perform(MockMvcRequestBuilders.get(Constants.API_V1 + "/employee/actives/")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(employee))));
        Mockito.verify(employeeService).findAllActivates();
    }
    
    @Test
    public void testGetAllDeactivates() throws Exception {
        System.out.println("EmployeeControllerTest - findAllDeactivates");
        Mockito.when(employeeService.findAllDeactivates()).thenReturn(Arrays.asList(employee));
        mockMvc.perform(MockMvcRequestBuilders.get(Constants.API_V1 + "/employee/deactivates/")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(employee))));
        Mockito.verify(employeeService).findAllDeactivates();
    }
    
    @Test
    public void testAddEmployee() throws Exception {
        System.out.println("EmployeeControllerTest - save");
        Mockito.when(employeeService.save(ArgumentMatchers.any(Employee.class))).thenReturn(employee);
        mockMvc.perform(MockMvcRequestBuilders.post(Constants.API_V1+"/employee/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(employee)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(employee)));
        Mockito.verify(employeeService).save(ArgumentMatchers.any(Employee.class));
    }
    
    @Test
    public void testUpdateEmployee() throws Exception {
            System.out.println("EmployeeControllerTest - update");
            Mockito.when(employeeService.getByCUI(ArgumentMatchers.any(Long.class))).thenReturn(employee);
            Mockito.when(employeeService.update(ArgumentMatchers.any(Employee.class), ArgumentMatchers.any(Long.class))).thenReturn( new Employee(12382L, "Diego Andres", "Marquez", 1, "12345678", "prueba@gmail.com", "diegoan44", true));
            Employee updated = new Employee(12382L, "Diego Andres", "Marquez", 1, "12345678", "prueba@gmail.com", "diegoan44", true);
            mockMvc.perform(MockMvcRequestBuilders.put(Constants.API_V1 + "/employee/12382")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updated)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Diego Andres"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value("Marquez"));
            Mockito.verify(employeeService).update(ArgumentMatchers.any(Employee.class), ArgumentMatchers.any(Long.class));
    }
}
