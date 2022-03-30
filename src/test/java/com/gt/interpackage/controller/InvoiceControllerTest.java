/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.interpackage.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.gt.interpackage.model.Invoice;
import com.gt.interpackage.source.Constants;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;
/**
 *
 * @author Luis
 */
@WebMvcTest (InvoiceController.class)
public class InvoiceControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private InvoiceService _invoiceService;
    
    private Invoice invoice;
    private ObjectMapper objectMapper;
    
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        LocalDate date = LocalDate.of(2020,05,02);
        invoice = new Invoice(1L, date, 200.0, 555);
        objectMapper = new ObjectMapper();
    }
    
//    @Test
//    public void testCreateInvoice() throws Exception {
//        System.out.println("InvoiceControllerTest - create");
//        Mockito.when(_invoiceService.save(ArgumentMatchers.any(Invoice.class))).thenReturn(invoice);
//        mockMvc.perform(MockMvcRequestBuilders.post(Constants.API_V1 + "/invoice/")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(invoice)))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(invoice)));
//        Mockito.verify(_invoiceService).save(ArgumentMatchers.any(Invoice.class));
//    }
//    
//    @Test
//    public void testGetInvoicesByClient() throws Exception {
//        System.out.println("InvoiceControllerTest - getInvoicesByClient");
//        Mockito.when(_invoiceService.getInvoicesByClient(555)).thenReturn(Arrays.asList(invoice));
//        mockMvc.perform(MockMvcRequestBuilders.get(Constants.API_V1 + "/invoice/client/555")
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(MockMvcResultMatchers.status().isOk())
//            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(invoice))));
//        Mockito.verify(_invoiceService).getInvoicesByClient(555);
//    }
    
}
