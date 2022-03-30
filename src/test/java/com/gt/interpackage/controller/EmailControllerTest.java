/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.interpackage.dto.EmailValuesDTO;
import com.gt.interpackage.model.Employee;
import com.gt.interpackage.service.EmailService;
import com.gt.interpackage.service.EmployeeService;
import com.gt.interpackage.source.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 *
 * @author bryan
 */
@WebMvcTest (EmailController.class)
public class EmailControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private EmailService emailService;
    
    @MockBean
    private EmployeeService employeeService;
    
    private ObjectMapper objectMapper;
    private EmailValuesDTO dto;
    private Employee employee;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.objectMapper = new ObjectMapper();
        this.dto = new EmailValuesDTO();
        this.employee = new Employee("Juan", "Gonzales", 1, "12345678", "juan123@gmail.com", "juan123", true);
    }
    
    @Test
    public void testSendEmailTemplate() throws Exception {
        System.out.println("EmailControllerTest - sendEmail");
        Mockito
                .when(emailService.sendEmailTemplate(ArgumentMatchers.any(EmailValuesDTO.class)))
                .thenReturn(true);
        Mockito
                .when(employeeService.getUserByUsernameOrEmail(ArgumentMatchers.any(String.class)))
                .thenReturn(employee);
        dto.setMailTo("juan123@gmail.com");
        dto.setUsername("juan123");
        mockMvc.perform(MockMvcRequestBuilders.post(Constants.API_V1 + "/email//send-email-forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Se ha enviado un correo, para realizar el cambio de contraseña."));
        Mockito.verify(emailService).sendEmailTemplate(ArgumentMatchers.any(EmailValuesDTO.class));
        Mockito.verify(employeeService).getUserByUsernameOrEmail(ArgumentMatchers.any(String.class));
    }
}
