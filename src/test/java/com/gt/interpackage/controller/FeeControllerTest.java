/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.interpackage.controller.FeeController;
import com.gt.interpackage.model.Fee;
import com.gt.interpackage.service.FeeService;
import com.gt.interpackage.source.Constants;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 *
 * @author bryan
 */
@WebMvcTest (FeeController.class)
public class FeeControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private FeeService feeService;
    
    private Fee fee;
    private ObjectMapper objectMapper;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fee = new Fee(1L, "Tarifa por operacion", 12.50);
        objectMapper = new ObjectMapper();
    }
    
    @Test
    public void testGetAllFees() throws Exception {
        System.out.println("FeeControllerTest - findAll");
        Mockito.when(feeService.findAll()).thenReturn(Arrays.asList(fee));
        mockMvc.perform(MockMvcRequestBuilders.get(Constants.API_V1 + "/fee/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(fee))));
        Mockito.verify(feeService).findAll();
    }
    
    @Test
    public void testGetFee() throws Exception {
        System.out.println("FeeControllerTest - getById");
        Mockito.when(feeService.getById(ArgumentMatchers.any(Long.class))).thenReturn(fee);
        mockMvc.perform(MockMvcRequestBuilders.get(Constants.API_V1 + "/fee/1/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Tarifa por operacion"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fee").value(12.5));
        Mockito.verify(feeService).getById(1L);
    }
    
    @Test
    public void testAddFee() throws Exception {
        System.out.println("FeeControllerTest - save");
        Mockito.when(feeService.save(ArgumentMatchers.any(Fee.class))).thenReturn(fee);
        mockMvc.perform(MockMvcRequestBuilders.post(Constants.API_V1 + "/fee/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fee)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(fee)));
        Mockito.verify(feeService).save(ArgumentMatchers.any(Fee.class));
    }
    
    @Test
    public void testUpdateFee() throws  Exception {
        System.out.println("FeeContollerTest - update");
        Mockito.when(feeService.getById(ArgumentMatchers.any(Long.class))).thenReturn(fee);
        Mockito.when(feeService.update(ArgumentMatchers.any(Fee.class), ArgumentMatchers.any(Long.class))).thenReturn(new Fee(1L, "Tarifa por operacion", 85.50));
        Fee updated = new Fee(1L, "Tarifa por operacion", 85.50);
        mockMvc.perform(MockMvcRequestBuilders.put(Constants.API_V1 + "/fee/1/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Tarifa por operacion"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fee").value(85.50));
        Mockito.verify(feeService).update(ArgumentMatchers.any(Fee.class), ArgumentMatchers.any(Long.class));
    }
}