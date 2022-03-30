/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.interpackage.model.Destination;
import com.gt.interpackage.service.DestinationService;
import com.gt.interpackage.service.RouteService;
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
@WebMvcTest (DestinationController.class)
public class DestinationControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private DestinationService destinationService;
    
    @MockBean 
    private RouteService routeService;
    
    private Destination destination;
    private ObjectMapper objectMapper;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        destination = new Destination(1L, "GUATEMALA-PETEN", "De Gautemala a Peten", 52.50);
        objectMapper = new ObjectMapper();
    }
    
    @Test
    public void testAddDestination() throws Exception {
        System.out.println("DestinationControllerTest - save");
        Mockito.when(destinationService
                .save(ArgumentMatchers.any(Destination.class)))
                .thenReturn(destination);
        mockMvc.perform(MockMvcRequestBuilders.post(Constants.API_V1 + "/destination/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(destination)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(destination)));
        Mockito.verify(destinationService).save(ArgumentMatchers.any(Destination.class));
    }
}