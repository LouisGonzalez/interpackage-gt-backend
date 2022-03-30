/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.interpackage.model.Client;
import com.gt.interpackage.model.Employee;
import com.gt.interpackage.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.gt.interpackage.model.Package;
import com.gt.interpackage.source.Constants;
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
@WebMvcTest (PackageController.class)
public class PackageControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private PackageService _packageService;
    
    private Package pack;
    private ObjectMapper objectMapper;
    
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        pack = new Package(1L, true, false, false, 100.0, 50.0, false, "Esta es una descripcion", null, 50.0, null, null);
        objectMapper = new ObjectMapper();
    }
    
    @Test
    public void testAddPackage() throws Exception {
        System.out.println("PackageControllerTest - save");
        Mockito.when(_packageService.addPackage(ArgumentMatchers.any(Package.class))).thenReturn(pack);
        mockMvc.perform(MockMvcRequestBuilders.post(Constants.API_V1 + "/package/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pack)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(pack)));
        Mockito.verify(_packageService).addPackage(ArgumentMatchers.any(Package.class));
    }
    
    @Test
    public void testUpdatePackage() throws Exception {
        System.out.println("PackageControllerTest - update");
        Mockito.when(_packageService.getById(ArgumentMatchers.any(Long.class))).thenReturn(pack);
        Mockito.when(_packageService.update(ArgumentMatchers.any(Package.class), ArgumentMatchers.any(Long.class))).thenReturn( new Package(1L, true, false, false, 75.0, 50.0, false, "Esta es una descripcion 2", null, 50.0, null, null));
        Package updated =  new Package(1L, true, false, false, 100.0, 50.0, false, "Esta es una descripcion", null, 50.0, null, null);
        mockMvc.perform(MockMvcRequestBuilders.put(Constants.API_V1 + "/package/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Esta es una descripcion 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.weight").value(75.0));
        Mockito.verify(_packageService).update(ArgumentMatchers.any(Package.class), ArgumentMatchers.any(Long.class));
    }
    
}
