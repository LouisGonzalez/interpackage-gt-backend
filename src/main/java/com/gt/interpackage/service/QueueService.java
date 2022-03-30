/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.service;

import com.gt.interpackage.model.Queue;
import com.gt.interpackage.repository.QueueRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bryan
 */
@Service
public class QueueService {
    
    @Autowired
    private QueueRepository queueRepository;
    
    public List<Queue> findByDestination(Long idDestination) {
      return queueRepository.findAllByPackages_Destination_Id(idDestination);
    }

    public void deletePackageOnQueue(Queue queue) {
        queueRepository.delete(queue);
    }
    
    public <S extends Queue> S save(S entity){
        return queueRepository.save(entity);
    }
}
