/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.service;

import com.gt.interpackage.model.Invoice;
import com.gt.interpackage.repository.InvoiceRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author Luis
 */
@Service
public class InvoiceService {
    
    @Autowired
    private InvoiceRepository _invRepository;
    
    public <S extends Invoice> S save(S entity){
        try {
            return _invRepository.save(entity);
        } catch(Exception e){
            return null;
        }
    }
    
    public List<Invoice> getInvoicesByClient(Integer nit){
        return _invRepository.getInvoicesByClient(nit);
    }
    
}
