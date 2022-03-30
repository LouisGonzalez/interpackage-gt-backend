/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author bryan
 */
@Entity
@Table (name = "queue")
public class Queue {
    
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long queue;
    
    @Column (nullable = false)
    private Integer position;
    
    @ManyToOne
    @JoinColumn (name = "id_package", nullable = false)
    private Package packages;

    public Queue() { }
    
    public Queue(Long queue, Package packages, Integer position) {
        this.queue = queue;
        this.packages = packages;
        this.position = position;
    }

    public Long getQueue() {
        return queue;
    }

    public void setQueue(Long queue) {
        this.queue = queue;
    }

    public Package getPackages() {
        return packages;
    }

    public void setPackages(Package packages) {
        this.packages = packages;
    }

    public Integer gettion() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
    
}
