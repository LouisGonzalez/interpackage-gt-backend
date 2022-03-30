package com.gt.interpackage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.interpackage.model.Checkpoint;
import com.gt.interpackage.model.Destination;
import com.gt.interpackage.model.Employee;
import com.gt.interpackage.model.EmployeeType;
import com.gt.interpackage.model.Route;
import com.gt.interpackage.service.CheckpointService;
import com.gt.interpackage.service.EmployeeService;
import com.gt.interpackage.service.EmployeeTypeService;
import com.gt.interpackage.service.PackageCheckpointService;
import com.gt.interpackage.service.RouteService;
import com.gt.interpackage.source.Constants;
import java.util.Optional;
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
@WebMvcTest (CheckpointController.class)
public class CheckpointControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private CheckpointService checkpointService;
            
    @MockBean
    private RouteService routeService;
    
    @MockBean
    private EmployeeTypeService employeeTypeService;
    
    @MockBean
    private EmployeeService employeeService;
    
    @MockBean
    private PackageCheckpointService packageCheckpointService;
    
    private Checkpoint checkpoint;
    private Employee employee;
    private Route route;
    private ObjectMapper objectMapper;
    private Destination destination;
    private Optional<Checkpoint> optionalCheckpoint;
    private EmployeeType employeeType;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        destination = new Destination(1L, "Guatemeala-Peten", "Destion de Guatemala a Peten", 45.50);
        route = new Route(1L, "Ruta 1", 45, 35, true, destination);
        employee = new Employee(12345678L, "Juan", "Gozales", 2, "12345678", "juan123@gmail", "juan123", true);
        checkpoint = new Checkpoint(1L, "Punto de control 1", 45.50, 25, 13, true, employee, route);
        optionalCheckpoint = Optional.of(checkpoint);
        objectMapper = new ObjectMapper();
        employeeType = new EmployeeType(2L, "operator", "Operador");
    }
    
    @Test
    public void testUpdateCheckpoint() throws Exception {
        System.out.println("DestinationController - updateCheckpoint");
        Mockito.when(
                employeeService
                        .getByCUI(ArgumentMatchers.any(Long.class)))
                .thenReturn(employee);
        Mockito.when(
                checkpointService 
                        .getCheckpointById(ArgumentMatchers.any(Long.class)))
                .thenReturn(optionalCheckpoint);
        Mockito.when(
                checkpointService
                        .routeAlreadyHasACheckpointWithName(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(String.class)))
                .thenReturn(false);
        Mockito.when(
                routeService
                        .existsById(ArgumentMatchers.any(Long.class)))
                .thenReturn(true);
        Mockito.when(
                checkpointService
                        .create(ArgumentMatchers.any(Checkpoint.class)))
                .thenReturn(checkpoint);
        Mockito.when(
                employeeTypeService
                        .getEmployeeTypeByName(ArgumentMatchers.any(String.class)))
                .thenReturn(employeeType);
        mockMvc.perform(MockMvcRequestBuilders.put(Constants.API_V1 + "/checkpoint/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(checkpoint)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Punto de control 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operationFee").value(45.50))
                .andExpect(MockMvcResultMatchers.jsonPath("$.queueCapacity").value(25))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(checkpoint)));
        Mockito.verify(employeeService).getByCUI(ArgumentMatchers.any(Long.class));
        Mockito.verify(employeeTypeService).getEmployeeTypeByName(ArgumentMatchers.any(String.class));
        Mockito.verify(checkpointService).getCheckpointById(ArgumentMatchers.any(Long.class));
        Mockito.verify(routeService).existsById(ArgumentMatchers.any(Long.class));
        Mockito.verify(checkpointService).create(ArgumentMatchers.any(Checkpoint.class));
    }
    
    @Test
    public void testUpdateAssignamentOperatorCheckpoint() throws Exception {
        System.out.println("DestinationController - updateAssignamentOperatorCheckpoint");
        Mockito.when(
                employeeService
                        .getByCUI(ArgumentMatchers.any(Long.class)))
                .thenReturn(employee);
        Mockito.when(
                checkpointService 
                        .getCheckpointById(ArgumentMatchers.any(Long.class)))
                .thenReturn(optionalCheckpoint);
        Mockito.when(
                checkpointService
                        .routeAlreadyHasACheckpointWithName(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(String.class)))
                .thenReturn(false);
        Mockito.when(
                routeService
                        .existsByName(ArgumentMatchers.any(String.class)))
                .thenReturn(true);
        Mockito.when(
                checkpointService
                        .create(ArgumentMatchers.any(Checkpoint.class)))
                .thenReturn(checkpoint);
        Mockito.when(
                employeeTypeService
                        .getEmployeeTypeByName(ArgumentMatchers.any(String.class)))
                .thenReturn(employeeType);
      /*  mockMvc.perform(MockMvcRequestBuilders.put(Constants.API_V1 + "/checkpoint/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(checkpoint)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.assignedOperator.name").value("Juan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.assignedOperator.cui").value(12345678L))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(checkpoint)));
        Mockito.verify(employeeService).getByCUI(ArgumentMatchers.any(Long.class));
        Mockito.verify(employeeTypeService).getEmployeeTypeByName(ArgumentMatchers.any(String.class));
        Mockito.verify(checkpointService).getCheckpointById(ArgumentMatchers.any(Long.class));
        Mockito.verify(routeService).existsById(ArgumentMatchers.any(Long.class));
        Mockito.verify(checkpointService).create(ArgumentMatchers.any(Checkpoint.class))*/;
    }
    
    @Test
    public void testGetCheckpoint() throws Exception {
        System.out.println("CheckpointController - getCheckpoint");
        Mockito.when(
                checkpointService 
                        .getCheckpointById(ArgumentMatchers.any(Long.class)))
                .thenReturn(optionalCheckpoint);
        mockMvc.perform(MockMvcRequestBuilders.get(Constants.API_V1 + "/checkpoint/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(checkpoint)));        
        Mockito.verify(checkpointService).getCheckpointById(ArgumentMatchers.any(Long.class));
    }
}
