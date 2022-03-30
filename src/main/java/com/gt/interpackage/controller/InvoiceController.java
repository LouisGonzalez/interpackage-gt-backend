/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.controller;

import com.gt.interpackage.service.InvoiceService;
import com.gt.interpackage.source.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gt.interpackage.model.Invoice;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Luis
 */
@CrossOrigin (origins = Constants.URL_FRONT_END, allowCredentials = "true")
@RestController
@RequestMapping (Constants.API_V1 + "/invoice")
public class InvoiceController {
    
    @Autowired
    private InvoiceService _invoiceService;
    
     /**
     * Metodo que realiza una llamada al servicio de Facturas para ejecutar 
     * la creacion de una nueva factura dentro de la base de datos
     * @param invoice
     * @return Factura creada
     */
    @PostMapping("/")
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice){
        try {
            Invoice savedInvoice = _invoiceService.save(invoice);
            return ResponseEntity.created(
            new URI("/invoice/"+savedInvoice.getId()))
                    .body(savedInvoice);
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/client/{nit}")
    public ResponseEntity<List<Invoice>> getInvoicesByClient(@PathVariable Integer nit){
        return ResponseEntity.ok(_invoiceService.getInvoicesByClient(nit));
    }
    
}
