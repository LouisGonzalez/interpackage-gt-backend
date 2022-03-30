package com.gt.interpackage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gt.interpackage.model.EmployeeType;
import java.util.Optional;
/**
 *
 * @author helmuth
 */
@Repository
public interface EmployeeTypeRepository extends JpaRepository<EmployeeType, Long> {
    
    /**
     * Metodo que se comunica con la base de datos para obtener un objeto
     * de tipo EmployeeType en base al nombre que se recibe como parametro.
     * @return 
     */
    public Optional<EmployeeType> findByName(String name);
}
