package com.gt.interpackage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gt.interpackage.repository.CheckpointRepository;
import com.gt.interpackage.model.Checkpoint;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 *
 * @author helmuth
 */
@Service
public class CheckpointService {
    
    @Autowired
    private CheckpointRepository checkpointRepository;
    
     
    /**
     * Metodo que llama al repositorio de puntos de control para crear un nuevo punto de control.
     * @param checkpoint 
     * @return Punto de control creado.
     */
    public Checkpoint create(Checkpoint checkpoint){
        return checkpointRepository.save(checkpoint);
    }
    
    /**
     * Metodo que llama al repositorio de puntos de control para buscar
     * si existe un punto de control cuyo id de ruta sea igual al que se 
     * recibe como parametro.
     * @param routeId 
     * @return  True | False
     */
    public boolean routeHasCheckpointsAssigned(Long routeId){
        return checkpointRepository.existsCheckpointByRouteId(routeId);
    }
    
    /**
     * Metodo que llama al repositorio de puntos de control para buscar
     * si existe un punto de control cuyo id de ruta y descripcion sean
     * iguales a los parametros que se reciben.
     * @param routeId 
     * @return  True | False
     */
    public boolean routeAlreadyHasACheckpointWithName(Long routeId, String description){
        return checkpointRepository.existsCheckpointByRouteIdAndDescription(routeId, description);
    }
    
     /**
     * Metodo que llama al repositorio de puntos de control para obtener todos 
     * los puntos de control y retornarlas en una paginacion. 
     * @param pageable
     * @return 
     */
    public Page<Checkpoint> getAll(Pageable pageable){
        return checkpointRepository.findAll(pageable);
    }
    
     /**
     * Metodo que llama al repositorio de puntos de control para obtener un
     * punto de control cuyo id sea igual al que se recibe como parametro. 
     * @param id
     * @return 
     */
    public Optional<Checkpoint> getCheckpointById(Long id){
        return checkpointRepository.findById(id);
    }
    
    /**
     * Metodo que llama al repositorio de puntod de control para eliminar 
     * el punto de control que se recibe como parametro. 
     * @param checkpoint 
     */
    public void delete(Checkpoint checkpoint){
        checkpointRepository.delete(checkpoint);
    }
    
    public List<Checkpoint> getAllByAssignedOperator(Long cui){
        return checkpointRepository.findAllByAssignedOperatorCUIAndActiveTrueOrderByRouteId(cui);
    }

    public List<Checkpoint> getAllCheckpointsByDestinationId(Long idDestination) {
        return checkpointRepository.findAllByRoute_Destination_IdAndRoute_ActiveAndActiveOrderById(idDestination, true, true);
    }
}
