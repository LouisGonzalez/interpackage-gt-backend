/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.service;

import com.gt.interpackage.model.Fee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gt.interpackage.repository.FeeRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author bryan
 */
@Service
public class FeeService {

    @Autowired
    private FeeRepository feeRepository;
    
    public List<Fee> findAll() {
        return feeRepository.findAll();
    }
    
    public Fee getById(Long id) throws Exception {
        try {
            Fee fee = feeRepository.getById(id);
            if (fee == null) return null; 
            if (fee.getName() != null) { }
            return fee;
        } catch (EntityNotFoundException e) {
            return null;
        }
    }
   
    
    public <S extends Fee> S save(S entity) {
        return feeRepository.save(entity);
    }
    
    public <S extends Fee> Fee update(S entity, Long id) throws Exception {
        Fee fee = getById(id);
        if (fee != null) {
            fee.setFee(entity.getFee());
            fee.setName(entity.getName());
            return feeRepository.save(fee);
        }   return null;
    }
}
