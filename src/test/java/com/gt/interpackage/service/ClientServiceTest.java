/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.service;

import com.gt.interpackage.repository.ClientRepository;
import org.mockito.Mock;
import com.gt.interpackage.model.Client;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author Luis
 */
public class ClientServiceTest {
    
    
    @Mock
    private ClientRepository _clientRepository;
    
    @InjectMocks
    private ClientService _clientService;
    
    private Client client;
    
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        client = new Client(1888L, "Jose Manuel", "Garcia", 22, 5555, "Quetzaltenango");      
    }
    
    @Test
    public void testGetByNit() throws Exception{
        System.out.println("ClientServiceTest - getByNit");
        Mockito.when(_clientRepository.findByNit(ArgumentMatchers.any(Integer.class))).thenReturn(client);
        Client searched = _clientService.getByNit(555);
        Assertions.assertThat(searched.getCui()).isEqualTo(1888L);
    }
    
    @Test
    public void testFindAll(){
        System.out.println("ClientServiceTest - findAll");
        Mockito.when(_clientRepository.findAll()).thenReturn(Arrays.asList(client));
        assertNotNull(_clientService.findAll());
    }
    
    @Test
    public void testSave(){
        System.out.println("ClientServiceTest - client");
        Mockito.when(_clientRepository.save(ArgumentMatchers.any(Client.class))).thenReturn(client);
        assertNotNull(_clientService.save(new Client("Juan Manuel", "Maldonado", 30, 7778, "Guatemala")));
    }
}
