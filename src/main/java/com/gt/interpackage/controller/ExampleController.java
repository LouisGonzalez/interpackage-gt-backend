/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.controller;

import com.gt.interpackage.handlers.QueueHandler;
import com.gt.interpackage.model.Queue;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author bryan
 */
@RestController
@RequestMapping ("/")
public class ExampleController {
    
    @Autowired
    private QueueHandler queueHandler;
    
    @GetMapping
    public String prueba(){
        queueHandler.verifiyQueue();
        return "Hello World";
    }
}
