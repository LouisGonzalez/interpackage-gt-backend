package com.gt.interpackage.service;

import org.mockito.Mock;
import com.gt.interpackage.model.Employee;
import com.gt.interpackage.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentMatchers;
/**
 *
 * @author Luis
 */
public class EmployeeServiceTest {
    
    @Mock
    private EmployeeRepository _empRepository;
    
    private Employee emp;
    
    @InjectMocks
    private EmployeeService _empService;
    
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        emp = new Employee(12355L, "Jose Manuel", "Garcia", 1, "12345678", "prueba@gmail.com", "josema12", true);
    }
    
    @Test
    public void testFindActivates(){
        System.out.println("EmployeeServiceTest - findActivates");
        Mockito.when(_empRepository.getAllActivates()).thenReturn(Arrays.asList(emp));
        assertNotNull(_empService.findAllActivates());
    }
    
    @Test
    public void testFindDeactivates(){
        System.out.println("EmployeeServiceTest - findDeactivates");
        Mockito.when(_empRepository.getAllDeactivates()).thenReturn(Arrays.asList(emp));
        assertNotNull(_empService.findAllDeactivates());
    }

    @Test
    public void testGetByCUI() throws Exception {
        System.out.println("EmployeeServiceTest - getByCUI");
        Mockito.when(_empRepository.getById(ArgumentMatchers.any(Long.class))).thenReturn(emp);
        Employee searched = _empService.getByCUI(12355L);
        Assertions.assertThat(searched.getUsername()).isEqualTo("josema12");
    }
    
    @Test
    public void testSave(){
        System.out.println("EmployeeServiceTest - save");
        Mockito.when(_empRepository.save(ArgumentMatchers.any(Employee.class))).thenReturn(emp);
        assertNotNull(_empService.save(new Employee("Karen Maria", "Hernandez", 1, "12345678", "prueba2@gmail.com", "prueba77", true)));
    }
    
    @Test
    public void testUpdate() throws Exception {
        System.out.println("EmployeeServiceTest - update");
        Mockito.when(_empRepository.getById(ArgumentMatchers.any(Long.class))).thenReturn(emp);
        Mockito.when(_empRepository.save(ArgumentMatchers.any(Employee.class))).thenReturn(new Employee(12355L, "Jose Fernando", "Gallardo", 1, "12345687", "prueba@gmail.com", "josema12", true));
        Employee updated = new Employee(12355L, "Jose Fernando", "Gallardo", 1, "12345687", "prueba@gmail.com", "josema12", true);
        Employee empUpdated = _empService.update(updated,12355L);
        assertNotNull(empUpdated);
        assertEquals(updated.getLastname(), empUpdated.getLastname());
    }
    
    @Test
    public void testGetUserByUsernameOrEmail() throws Exception {
        System.out.println("EmployeeServiceTest - getUserByUsernameOrEmail");
        Mockito
                .when(_empRepository.getByUsernameOrEmail(ArgumentMatchers.any(String.class), ArgumentMatchers.any(String.class)))
                .thenReturn(emp);
        Employee searched = _empService.getUserByUsernameOrEmail("prueba@gmail.com");
        Assertions.assertThat(searched.getEmail()).isEqualTo("prueba@gmail.com");
    }
    
    @Test
    public void testGetUserByTokenPassword() throws Exception {
        System.out.println("EmployeeServiceTest - getUserByTokenPassword");
        String tokenPassword = "asdf123asdf564asdf321a65sd";
        emp.setTokenPassword(tokenPassword);
        Mockito
                .when(_empRepository.findByTokenPassword(ArgumentMatchers.any(String.class)))
                .thenReturn(emp);
        Employee searched = _empService.getUserByTokenPassword(tokenPassword);
        Assertions.assertThat(searched.getTokenPassword()).isEqualTo(tokenPassword);
    }
}
