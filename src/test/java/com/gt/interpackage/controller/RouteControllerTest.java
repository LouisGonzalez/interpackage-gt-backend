/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.interpackage.dto.FilterDateDTO;
import com.gt.interpackage.dto.TopRouteDTO;
import com.gt.interpackage.model.Destination;
import com.gt.interpackage.model.Route;
import com.gt.interpackage.service.CheckpointService;
import com.gt.interpackage.service.RouteService;
import com.gt.interpackage.source.Constants;
import java.util.Arrays;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 *
 * @author bryan
 */
@WebMvcTest (RouteController.class)
public class RouteControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private RouteService routeService;
    
    @MockBean
    private EntityManagerFactory EntityManagerFactory;
    
    @MockBean
    private EntityManager entityManager;
    
    @MockBean
    private CheckpointService checkpointService;
    
    private Destination destination;
    private Route route;
    private ObjectMapper objectMapper;
    private Page<Route> routes;
    private TopRouteDTO dto;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        destination = new Destination(1L, "Guatemeala-Peten", "Destion de Guatemala a Peten", 45.50);
        route = new Route(1L, "Ruta 1", 45, 500, true, destination);
        objectMapper = new ObjectMapper();
        routes = Page.empty();
        dto = new TopRouteDTO(45, "Ruta 1", "Guatemala");
    }
    
    @Test
    public void testGetRoutesByActive() throws Exception {
        Mockito.when(
                routeService
                        .getRoutesByActive(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.any(Boolean.class)))
                .thenReturn(routes);
        mockMvc.perform(MockMvcRequestBuilders.get(Constants.API_V1 + "/route/list/true")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        Mockito.verify(routeService).getRoutesByActive(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.any(Boolean.class));
    }
    
    @Test
    public void testGetMostPopularRouteFilter() throws Exception {
        Mockito.when(
                routeService
                        .getTopRoute(ArgumentMatchers.any(EntityManager.class), ArgumentMatchers.any(String.class), ArgumentMatchers.any(String.class)))
                .thenReturn(Arrays.asList(dto));
        FilterDateDTO dateDTO = new FilterDateDTO("2018-02-02", "2018-04-04");
        mockMvc.perform(MockMvcRequestBuilders.post(Constants.API_V1+ "/route/most-popular-route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dateDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        Mockito.verify(routeService).getTopRoute(ArgumentMatchers.any(EntityManager.class), ArgumentMatchers.any(String.class), ArgumentMatchers.any(String.class));                
    }
}
