package com.gt.interpackage.repository;
import com.gt.interpackage.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author Luis
 */
@Repository
public interface AuthRepository extends JpaRepository<Employee, Long>{
    
    public Employee findByUsernameAndPasswordAndActivoTrue(String username, String password);
    
}
