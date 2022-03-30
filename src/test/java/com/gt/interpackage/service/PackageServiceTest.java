/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.service;

import com.gt.interpackage.repository.PackageRepository;
import org.mockito.Mock;
import com.gt.interpackage.model.Package;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Luis
 */
public class PackageServiceTest {
    
    @Mock
    private PackageRepository _packageRepository;
    
    private Package pack;
    
    @InjectMocks
    private PackageService _packageService;
    
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        pack = new Package(1L, true, false, false, 100.0, 50.0, false, "Esta es una descripcion", null, 50.0, null, null);
    }
    
    @Test
    public void testGetById() throws Exception {
        System.out.println("PackageServiceTest - getById");
        String description = "Esta es una descripcion";
        Mockito.when(_packageRepository.getById(ArgumentMatchers.any(Long.class))).thenReturn(pack);
        Package searched = _packageService.getById(1L);
        Assertions.assertThat(searched.getDescription()).isEqualTo(description);
    }
    
    @Test
    public void testAddPackage(){
        System.out.println("PackageServiceTest - addPackage");
        Mockito.when(_packageRepository.save(ArgumentMatchers.any(Package.class))).thenReturn(pack);
        assertNotNull(_packageService.addPackage(new Package(true, false, false, 100.0, 50.0, false, "Esta es una descripcion", null, 50.0, null, null)));
    }
    
    @Test
    public void testUpdate() throws Exception {
        System.out.println("PackageServiceTest - update");
        Mockito.when(_packageRepository.getById(ArgumentMatchers.any(Long.class))).thenReturn(pack);
        Mockito.when(_packageRepository.save(ArgumentMatchers.any(Package.class))).thenReturn(new Package(1L, true, false, false, 100.0, 50.0, false, "Esta es una descripcion 2", null, 50.0, null, null));
        Package updated = new Package(1L, true, false, false, 100.0, 50.0, false, "Esta es una descripcion 2", null, 50.0, null, null);
        Package packUpdated = _packageService.update(updated, 1L);
        assertNotNull(packUpdated);
        assertEquals(updated.getDescription(), packUpdated.getDescription());
    }
    
    
}
