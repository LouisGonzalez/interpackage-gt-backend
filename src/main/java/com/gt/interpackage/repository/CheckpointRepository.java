package com.gt.interpackage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gt.interpackage.model.Checkpoint;
import java.util.List;
/**
 *
 * @author helmuth
 */
@Repository
public interface CheckpointRepository extends JpaRepository<Checkpoint, Long>{
    
     /**
     * Metodo que busca en la base de datos si existe por lo menos un punto de control cuyo 
     * id de ruta sea igual al que se recibe como parametro.
     * @param routeId 
     * @return True | False
     */
    public boolean existsCheckpointByRouteId(Long routeId);
    
    public boolean existsCheckpointByRouteIdAndDescription(Long routeId, String description);
    
    public List<Checkpoint> findAllByAssignedOperatorCUIAndActiveTrueOrderByRouteId(Long cui);
    
    public List<Checkpoint> findAllByRoute_Destination_IdAndActiveOrderById(Long idDestination, Boolean active);

    List<Checkpoint> findAllByRoute_Destination_IdAndRoute_ActiveAndActiveOrderById(Long idDestination, Boolean activeRoute, Boolean activeCheckpoint);
}
