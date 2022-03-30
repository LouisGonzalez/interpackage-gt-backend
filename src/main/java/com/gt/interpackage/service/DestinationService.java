package com.gt.interpackage.service;

import com.gt.interpackage.repository.DestinationRepository;
import com.gt.interpackage.model.Destination;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
/**
 * @author bryan
 * @author helmuth
 */
@Service
public class DestinationService {
    
    @Autowired
    private DestinationRepository destinationRepository;
    
    public <S extends Destination> S save(S entity) {
        if (destinationRepository.existsDestinationByName(entity.getName())) return null;
        return destinationRepository.save(entity);
    }
    
    /**
     * Metodo que llama al repositorio de destinos para obtener todas aquellos 
     * rutas cuyo nombre inicie con el nombre que se recibe como parametro.
     * @param name
     * @return Listado de destinos obtenidos. 
     */
    public List<Destination> findByName(String name){
        return destinationRepository.findByNameStartingWith(name);
    }
    
    /**
     * Metodo que llama al repositorio de destinos para obtener todos 
     * los destinos y retornarlos en una paginacion. 
     * @param pageable
     * @return 
     */
    public Page<Destination> getAll(Pageable pageable){
        return destinationRepository.findAll(pageable);
    }
    
    /**
     * Metodo que llama al repositorio de destinos para eliminar 
     * el destino que se recibe como parametro. 
     * @param destination 
     */
    public void delete(Destination destination){
        destinationRepository.delete(destination);
    }
    
    /**
     * Metodo que llama al repositorio de destinos para obtener un 
     * destino cuyo id sea igual al que se recibe como parametro. 
     * @param id
     * @return 
     */
    public Optional<Destination> getDestinationById(Long id){
        return destinationRepository.findById(id);
    }
    
    /**
     * Metodo que llama al repositorio de destinos para consultar si existe un
     * destino cuyo nombre sea el parametro name que se recibe y cuyo id no sea
     * el parametro id.
     * @param name
     * @return True o False. 
     */
    public boolean exists(String name, Long id){
        return destinationRepository.existsDestinationByNameAndIdIsNot(name, id);
    }

    public List<Destination> findAll(){
        return destinationRepository.findAll();
    }
    
    public boolean existsDestinationByName(String name) {
        return destinationRepository.existsDestinationByName(name);
    }

}
