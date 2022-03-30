/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.service;

import com.gt.interpackage.repository.InvoiceRepository;
import com.gt.interpackage.model.Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import org.assertj.core.api.LocalDateAssert;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.assertj.core.api.Assertions;
import org.mockito.InjectMocks;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
/**
 *
 * @author Luis
 */
public class InvoiceServiceTest {
    
    @Mock
    private InvoiceRepository _invRepository;
    
    private Invoice invoice;
    
    @InjectMocks
    private InvoiceService _invoiceService;
    
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        LocalDate date = LocalDate.of(2020,05,02);
        invoice = new Invoice(1L, date, 50.0, 555);        
    }
    
    @Test
    public void testSave(){
        System.out.println("InvoiceServiceTest - save");
        Mockito.when(_invRepository.save(ArgumentMatchers.any(Invoice.class))).thenReturn(invoice);
        LocalDate date = LocalDate.of(2020,05,02);
        assertNotNull(_invoiceService.save(new Invoice(date, 23.0, 555)));
    }
    
    @Test
    public void testGetInvoicesByClient() throws Exception{
        System.out.println("InvoiceServiceTest - getInvoicesByClient");
        Mockito.when(_invRepository.getInvoicesByClient(ArgumentMatchers.any(Integer.class))).thenReturn(Arrays.asList(invoice));
        assertNotNull(_invoiceService.getInvoicesByClient(555));
    }
}
