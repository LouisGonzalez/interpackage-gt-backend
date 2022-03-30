package com.gt.interpackage.repository;

import com.gt.interpackage.model.Employee;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
/**
 *
 * @author Luis
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    @Query(value = "SELECT * FROM employee e WHERE e.activo = true", nativeQuery = true)
    List<Employee> getAllActivates();
    
    @Query(value = "SELECT * FROM employee e WHERE e.activo = true AND e.type <> 1", nativeQuery = true)
    List<Employee> getAllActivatesNotAdmin();
    
    /**
     * Metodo que obtiene y retorna desde la base de datos todos los empleados
     * cuyo nombre inicie con el nombre que se recibe como parametro y que sean 
     * del tipo de empleado que tambien se recibe como parametro..
     * @param cui
     * @param employeeType
     * @return Listado
     */
    
    @Query(value = "SELECT * FROM Employee WHERE CAST(cui AS TEXT) LIKE ?1% AND type = ?2 AND activo=true", nativeQuery = true)
    public Optional<List<Employee>> findByCuiContainsAndEmployeeTypeIs(String cui, Integer employeeType);
    
    @Query(value = "SELECT * FROM employee e WHERE e.activo = false", nativeQuery = true)
    List<Employee> getAllDeactivates();
    
    public Employee getByUsernameOrEmail(String username, String email);
    
    public Employee findByTokenPassword(String tokenPassword);
    
    /**
     * Metodo que consulta desde la base de datos si existe un destino con el nombre 
     * que se recibe como parametro name  y cuyo id no sea igual al que se recibe como 
     * segundo parametro.
     * @param name
     * @param id
     * @return 
     */
    public boolean existsEmployeeByUsername(String username);
    
    public boolean existsEmployeeByCUI(Long CUI);

}
