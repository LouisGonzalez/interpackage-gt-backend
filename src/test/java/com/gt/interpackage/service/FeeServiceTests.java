/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.gt.interpackage.service;

import com.gt.interpackage.model.Fee;
import com.gt.interpackage.repository.FeeRepository;
import java.util.Arrays;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author bryan
 */
public class FeeServiceTests {
    
    @Mock
    private FeeRepository feeRepository;
    
    private Fee fee;
    
    @InjectMocks
    private FeeService feeService;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fee = new Fee(1L, "Tarifa por operacion", 12.50);
    }
    
    /**
     * Test of findAll method, of class FeeService.
     */
    @Test
    public void testFindAll() {
        System.out.println("FeeServiceTest - findAll");
        Mockito.when(feeRepository.findAll()).thenReturn(Arrays.asList(fee));
        assertNotNull(feeService.findAll());
    }

    /**
     * Test of getById method, of class FeeService.
     */
    @Test
    public void testGetById() throws Exception {
        System.out.println("FeeServiceTest - getById");
        String nameFee = "Tarifa por operacion";
        Mockito.when(feeRepository.getById(ArgumentMatchers.any(Long.class))).thenReturn(fee);
        Fee searched = feeService.getById(1L);
        Assertions.assertThat(searched.getName()).isEqualTo(nameFee);
    }

    /**
     * Test of save method, of class FeeService.
     */
    @Test
    public void testSave() {
        System.out.println("FeeServiceTest - save");
        Mockito.when(feeRepository.save(ArgumentMatchers.any(Fee.class))).thenReturn(fee);
        assertNotNull(feeService.save(new Fee("Tarifa por operacion", 15.50)));
    }

    /**
     * Test of update method, of class FeeService.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("FeeServiceTest - update");
        Mockito.when(feeRepository.getById(ArgumentMatchers.any(Long.class))).thenReturn(fee);
        Mockito.when(feeRepository.save(ArgumentMatchers.any(Fee.class))).thenReturn(new Fee(1L, "Tarifa por operacion", 85.50));
        Fee updated = new Fee(1L, "Tarifa por operacion", 85.50);
        Fee feeUpdate = feeService.update(updated, 1L);
        assertNotNull(feeUpdate);
        assertEquals(updated.getFee(), feeUpdate.getFee());
    }
    
}
