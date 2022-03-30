/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.request;

import com.gt.interpackage.model.Client;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 *
 * @author Luis
 */
public class ClientReport {
    
    
    private Long cui;
    
    private String name;
    
    private String lastname;
    
    private Integer age;
    
    private Integer nit;
    
    private String address;
    
    
    public ClientReport(){}
    
    public ClientReport(Long cui, String name, String lastname, Integer age, Integer nit, String address){
        this.cui = cui;
        this.name = name;
        this.lastname = lastname;
        this.age = age;
        this.nit = nit;
        this.address = address;
    }

}
