/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.gt.interpackage.repository;

import com.gt.interpackage.model.Destination;
import com.gt.interpackage.model.Queue;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author bryan
 */
@Repository
public interface QueueRepository extends JpaRepository<Queue, Long> {
    
    public List<Queue> findAllByPackages_Destination_Id(Long id_Destination);

}
