package com.gt.interpackage.service;

import com.gt.interpackage.repository.PackageCheckpointRepository;
import com.gt.interpackage.model.PackageCheckpoint;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author helmuth
 */
@Service
public class PackageCheckpointService {
    
    @Autowired
    private PackageCheckpointRepository packageCheckpointRepository;
    
    public boolean existsAnyRegisterOfCheckpointById(Long id){
        return packageCheckpointRepository.existsPackageCheckpointByCheckpointId(id);
    }
    
    /**
     * Metodo que utliza el repositorio de paquetes-puntos de control para obtener 
     * un objeto de tipo PackageCheckpoint en base al id del paquete que se recibe 
     * como parametro.
     * @param id
     * @return 
     */
    public PackageCheckpoint getPackageCheckpoint(Long id){
        return packageCheckpointRepository.findByPackagesIdAndCurrentCheckpointTrue(id);
    }
    
    /**
     * Metodo que utiliza el repositorio de paquetes-puntos de control para 
     * obtener el tiempo total que un paquete ha estado en ruta segun el id
     * del paquete que se recibe como parametro.
     * @param id
     * @return 
     */
    public String getTimeOnRouteByPackageId(Long id){
        return packageCheckpointRepository.getTimeOnRouteByPackageId(id);
    }
    
    /**
     * Metodo que utliza el repositorio de paquetes-puntos de control para obtener 
     * todos los  objetos de tipo PackageCheckpoint en base al id del punto de control
     * que se recibe como parametro y cuya columna current_chekpoint sea true.
     * @param id
     * @return 
     */
    public List<PackageCheckpoint> getAllPackageCheckpointOnCheckpoint(Long id){
        return packageCheckpointRepository.findAllByCheckpointIdAndCurrentCheckpointTrue(id);
    }
 
    public void update(PackageCheckpoint packageCheckpoint){
        packageCheckpointRepository.update(
            packageCheckpoint.getCurrentCheckpoint(),
            packageCheckpoint.getTimeOnCheckpoint(),
            packageCheckpoint.getDate(),
            packageCheckpoint.getPackages().getId(),
            packageCheckpoint.getCheckpoint().getId());
    }
    
    public PackageCheckpoint getByCheckpointIdPackageId(Long checkpointId, Long packageId){
        return packageCheckpointRepository.findByCheckpointIdAndPackagesId(checkpointId, packageId);
    }
    
    public Long getNextCheckpointId(Long packageId, Long routeId){
        return packageCheckpointRepository.getNextCheckpointId(packageId, routeId);
    }
    
    public void create(PackageCheckpoint packageCheckpoint){
        packageCheckpointRepository.create(
            packageCheckpoint.getCurrentCheckpoint(),
            packageCheckpoint.getPackages().getId(),
            packageCheckpoint.getCheckpoint().getId());
    }
    
}
