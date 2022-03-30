/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.interpackage.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.gt.interpackage.model.Client;
import com.gt.interpackage.model.Fee;
import com.gt.interpackage.source.Constants;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 *
 * @author Luis
 */
@WebMvcTest (ClientController.class)
public class ClientControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ClientService _clientService;
    
    private Client client;
    private ObjectMapper objectMapper;
    
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        client = new Client(1888L, "Jose Manuel", "Garcia", 22, 5555, "Quetzaltenango");
        objectMapper = new ObjectMapper();
    }
    
    @Test
    public void testGetAllClients() throws Exception {
        System.out.println("ClientControllerTest - findAll");
        Mockito.when(_clientService.findAll()).thenReturn(Arrays.asList(client));
        mockMvc.perform(MockMvcRequestBuilders.get(Constants.API_V1 + "/client/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(client))));
        Mockito.verify(_clientService).findAll();        
    }
    
    @Test
    public void testGetClientByNit() throws Exception {
        System.out.println("ClientControllerTest - getByNit");
        Mockito.when(_clientService.getByNit(ArgumentMatchers.any(Integer.class))).thenReturn(client);
        mockMvc.perform(MockMvcRequestBuilders.get(Constants.API_V1 + "/client/5555/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jose Manuel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cui").value(1888L));
        Mockito.verify(_clientService).getByNit(5555);
    }
    
    @Test 
    public void testAddClient() throws Exception {
        System.out.println("ClientControllerTest - save");
        Mockito.when(_clientService.save(ArgumentMatchers.any(Client.class))).thenReturn(client);
        mockMvc.perform(MockMvcRequestBuilders.post(Constants.API_V1 + "/client/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(client)));
        Mockito.verify(_clientService).save(ArgumentMatchers.any(Client.class));
        
    }
}
