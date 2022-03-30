/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.pk;

import com.gt.interpackage.model.Checkpoint;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author bryan
 */
public class PKPackageCheckpoint implements Serializable {

    private Checkpoint checkpoint;
    private com.gt.interpackage.model.Package packages;

    public PKPackageCheckpoint() { }
    
    public PKPackageCheckpoint(Checkpoint checkpoint, com.gt.interpackage.model.Package packages) {
        this.checkpoint = checkpoint;
        this.packages = packages;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.checkpoint);
        hash = 59 * hash + Objects.hashCode(this.packages);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PKPackageCheckpoint other = (PKPackageCheckpoint) obj;
        if (!Objects.equals(this.checkpoint, other.checkpoint)) {
            return false;
        }
        return Objects.equals(this.packages, other.packages);
    }

    public Checkpoint getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(Checkpoint checkpoint) {
        this.checkpoint = checkpoint;
    }

    public com.gt.interpackage.model.Package getPackages() {
        return packages;
    }

    public void setPackages(com.gt.interpackage.model.Package packages) {
        this.packages = packages;
    }
    
}
