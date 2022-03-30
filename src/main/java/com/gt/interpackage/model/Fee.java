/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author bryan
 */
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table (name = "fee")
public class Fee {
    
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    
    @Column (nullable = false, length = 75)
    private String name;
    
    @Column (nullable = false, scale = 2)
    private Double fee;

    public Fee() {}
    
    public Fee(Long id, String name, Double fee) {
        this.id = id;
        this.name = name;
        this.fee = fee;
    }

    public Fee(String name, Double fee) {
        this.name = name;
        this.fee = fee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }
    
}
