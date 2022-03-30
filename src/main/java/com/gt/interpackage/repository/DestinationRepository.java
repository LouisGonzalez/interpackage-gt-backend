package com.gt.interpackage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gt.interpackage.model.Destination;
import java.util.List;
import org.springframework.stereotype.Repository;
/**
 * 
 * @author helmuth
 */
@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long>{
    
    /**
     * Metodo que obtiene y retorna desde la base de datos todas los destinos
     * cuyo nombre inicie con el nombre que se recibe como parametro.
     * @param name 
     * @return Listado de destinos obtenidos.
     */
    List<Destination> findByNameStartingWith(String name);
    
    /**
     * Metodo que consulta desde la base de datos si existe un destino con el nombre 
     * que se recibe como parametro name  y cuyo id no sea igual al que se recibe como 
     * segundo parametro.
     * @param name
     * @param id
     * @return 
     */
    public boolean existsDestinationByNameAndIdIsNot(String name, Long id);
    
    public boolean existsDestinationByName(String name);
}