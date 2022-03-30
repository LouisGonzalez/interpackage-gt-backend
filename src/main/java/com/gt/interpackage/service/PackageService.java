package com.gt.interpackage.service;

import com.gt.interpackage.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gt.interpackage.model.Package;
import java.util.List;
import org.springframework.data.domain.Page;
import javax.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
/**
 *
 * @author Luis
 */
@Service
public class PackageService {
    
    @Autowired
    private PackageRepository _packageRepository;
    
    /*
     * Metodo que llama al repositorio de paquetes para obtener
     * los datos del paquete que se recibe como parametro
    */
    public Package getById(Long id)   {
        try {
            return  _packageRepository.getById(id);
        } catch(EntityNotFoundException e){
            return null;
        }
    }
    
    /*
     * Metodo que llama al repositorio de paquetes para obtener una paginacion 
     * con todos los paquetes que se encuentran en destino y no han sido retirados.
    */
    public Page<Package> getAllAtDestination(Pageable pageable){
        return _packageRepository.findAllByAtDestinationTrueAndRetiredFalse(pageable);
    }
    
    
    /*
     * Metodo que llama al repositorio de paquetes para obtener una paginacion 
     * con todos los paquetes que se encuentran en ruta.
    */
    public Page<Package> getAllOnRoute(Pageable pageable){
        return _packageRepository.findAllByOnWayTrue(pageable);
    }
    
    /*
     * Metodo que llama al repositorio de paquetes para agregar
     * un nuevo paquete a la bse de datos
    */
    public Package addPackage(Package pack){
       return _packageRepository.save(pack);
    }
    
    /*
     * Metodo que llama al repositorio de paquetes para actualizar
     * un paquete especifico
    */
    public <S extends Package> Package update(S entity, Long id) throws Exception {
        Package pack = getById(id);
        if(pack != null) {
            pack.setDescription(entity.getDescription());
            pack.setOnWay(entity.getOnWay());
            pack.setRetired(entity.getRetired());
            pack.setAtDestination(entity.getAtDestination());
            pack.setUnitTotal(entity.getUnitTotal());
            pack.setRoute(entity.getRoute());
            pack.setWeight(entity.getWeight());
            return _packageRepository.save(pack);
        } return null;
    }
    
    /**
     * Metodo que hace uso del repositorio de paquetes para obtener 
     * un listado de paquetes cuyo id de factura sea igual al parametro que se
     * recibe.
     * @param id
     * @return 
     */
    public List<Package> getPackagesOnRouteByInoviceId(Long id){
        return _packageRepository.findAllByInvoiceIdAndOnWayTrue(id);
    }
    
    public List<Package> getPackagesByInvoice(Long id_invoice){
        return _packageRepository.getPackagesByInvoice(id_invoice);
    }
    
}
