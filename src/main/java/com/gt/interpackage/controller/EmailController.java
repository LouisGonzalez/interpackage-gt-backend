/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.controller;

import com.gt.interpackage.dto.EmailValuesDTO;
import com.gt.interpackage.model.Employee;
import com.gt.interpackage.service.EmailService;
import com.gt.interpackage.service.EmployeeService;
import com.gt.interpackage.source.Constants;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author bryan
 */

@CrossOrigin (origins = Constants.URL_FRONT_END, allowCredentials = "true")
@RestController
@RequestMapping (Constants.API_V1 + "/email")
public class EmailController {
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private EmployeeService employeeService;
    
    @Value ("${spring.mail.username}")
    private String mailFrom;
    
    @PostMapping ("/send-email-forgot-password")
    public ResponseEntity<?> sendEmailTemplate(@RequestBody EmailValuesDTO dto) {
        try {
            dto.setMailFrom(mailFrom);
            dto.setSubject("Cambio de contraseña.");
            dto.setTokenPassword(UUID.randomUUID().toString());
            emailService.sendEmailTemplate(dto);
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Se ha enviado un correo, para realizar el cambio de contraseña.");
            Employee employee = employeeService.getUserByUsernameOrEmail(dto.getMailTo());
            employee.setTokenPassword(dto.getTokenPassword());
            employeeService.save(employee);
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // 400 Bad Request
        }
    }
   
}
