/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.controller;

import com.gt.interpackage.model.Queue;
import com.gt.interpackage.service.QueueService;
import com.gt.interpackage.source.Constants;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Luis
 */
@CrossOrigin (origins = Constants.URL_FRONT_END, allowCredentials = "true")
@RestController
@RequestMapping (Constants.API_V1 + "/queue")
public class QueueController {
    
    @Autowired
    private QueueService _queueService;
    
    @PostMapping("/")
    public ResponseEntity<Queue> addQueue(@RequestBody Queue queue) {
        try { 
            Queue savedQueue = _queueService.save(queue);
            System.out.println(savedQueue);
            return ResponseEntity.created(
                    new URI("/queue/" + savedQueue.getQueue()))
                    .body(savedQueue);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
    }

}
