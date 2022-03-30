package com.gt.interpackage.controller;

import com.gt.interpackage.handlers.QueueHandler;
import com.gt.interpackage.service.PackageCheckpointService;
import com.gt.interpackage.source.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gt.interpackage.model.PackageCheckpoint;
import com.gt.interpackage.model.Checkpoint;
import com.gt.interpackage.model.Package;
import com.gt.interpackage.service.CheckpointService;
import com.gt.interpackage.service.PackageService;
import com.gt.interpackage.service.RouteService;
import java.sql.Time;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author helmuth
 */
@RestController
@RequestMapping(Constants.API_V1 + "/package-checkpoint")
public class PackageChekpointController {
    
    @Autowired
    private PackageCheckpointService packageCheckpointService;
    
    @Autowired
    private CheckpointService checkpointService;
    
    @Autowired
    private RouteService routeService;
    
    @Autowired
    private PackageService packageService;
    
    @Autowired
    private QueueHandler queueHandler;
    
    /**
     * Metodo que recibe una peticion de tipo GET para obtener un objeto
     * de tipo PackageChekpoint cuyo id de paquete sea el que se recibe 
     * como parametro. Se modifica el valor del atributo timeOnCheckpoint
     * y se setea dentro del mismo la cantidad de tiempo que el paquete 
     * ha estado en ruta, es decir, la sumatoria de tiempos de cada uno 
     * de los puntos de control registrados.
     * @param id
     * @return 
     */
    @GetMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<PackageCheckpoint> getPackageCheckpoint(@PathVariable Long id){
        try{
            PackageCheckpoint tempPackageCheckpoint = packageCheckpointService.getPackageCheckpoint(id);
            
            String timeOnRoute = packageCheckpointService.getTimeOnRouteByPackageId(id);
            if(timeOnRoute != null)
                tempPackageCheckpoint.setTimeOnCheckpoint(Time.valueOf(timeOnRoute) );
            else
                tempPackageCheckpoint.setTimeOnCheckpoint(new Time(00,00,00));
            
            return ResponseEntity.ok(tempPackageCheckpoint);
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Metodo que recibe una peticion de tipo GET para obtener el tiempo
     * total que un paquete cuyo id de paquete sea el que se recibe 
     * como parametro dentro de la ruta hasta el momento de la peticion.
     * @param id
     * @return 
     */
    @GetMapping("time/{id}")
    @CrossOrigin
    public ResponseEntity<String> getTimeOnRoute(@PathVariable Long id){
        try{
            return ResponseEntity.ok(packageCheckpointService.getTimeOnRouteByPackageId(id));
        } catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity("Error en el servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Metodo que recibe una peticion GET para obtener todos los objetos de tipo PakcageCheckpoint
     * cuyo id checkpoint sea igual al que se recibe como parametro y la columna currentCheckpoint 
     * sea igual a true.
     * @param id
     * @return 
     */
    @GetMapping("list/{id}")
    @CrossOrigin
    public ResponseEntity<List<PackageCheckpoint>> getAllPackageCheckpointOnCheckpoint(@PathVariable Long id){
        try{
            return ResponseEntity.ok(packageCheckpointService.getAllPackageCheckpointOnCheckpoint(id));
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    /**
     * Metodo que recibe una peticion PATCH para procesar un paquete desde un punto de control.
     * 1. Buscar el siguiente punto de control de la ruta
     *      1.1 Si existe otro punto de control
     *          a.Verificar si el siguiente punto de control aun tiene espacio en cola
     *              -Si
     *              PackageCheckpoint(Tupla existente)
     *                  time_on_checkpoint = Valor recibido como parametro
     *                  currentCheckpoint = false
     *          
     *              PackageCheckpoint(Tupla nueva)
     *                  time_on_checkpoint = null
     *                  currentCheckpoint = true
     *                  id_checkpoint = id del punto de control siguiente
     *                  id_package = Valor recibido como parametro
     * 
     *              Checkpoint(Actual)
     *                  packages_on_queue = packages_on_queue - 1
     * 
     *              Checkpoint(Siguiente)
     *                  packages_on_queue = packages_on_queue + 1
     * 
     *              -No
     *              Responder con un mensaje indicando el error.
     * 
     *      1.2 Si no existe otro punto de control
     *          -Package:
     *              at_destination = true
     *              on_way = false
     *          
     *          -PackageCheckpoint(Tupla Existente)
     *              time_on_checkpoint = Valor recibido como parametro
     *              current_Checkpoint = false
     *          
     *          -Checkpoint
     *              packages_on_queue = packages_on_queue - 1 
     * 
     *          -Route
     *              packages_on_route = packages_on_route - 1 
     * @param packageCheckpoint
     * @return 
     */
    @PatchMapping()
    @CrossOrigin
    public ResponseEntity<PackageCheckpoint> processPackage(@RequestBody PackageCheckpoint packageCheckpoint){
        try{
            if(packageCheckpoint.getTimeOnCheckpoint() == null)
                return new ResponseEntity("Tiempo es un campo obligatorio", HttpStatus.BAD_REQUEST);
            
            if(packageCheckpoint.getTimeOnCheckpoint().toString().isBlank() || packageCheckpoint.getTimeOnCheckpoint().toString().isEmpty())
                return new ResponseEntity("Tiempo no valido", HttpStatus.BAD_REQUEST);
            
            //Obtener instancia con todos los datos almacenados en la base de datos.
            PackageCheckpoint tempPackageCheckpoint = packageCheckpointService.getByCheckpointIdPackageId(
                packageCheckpoint.getCheckpoint().getId(),
                packageCheckpoint.getPackages().getId());
            
            //Obtener el id del siguiente punto de control.
            Long nextCheckpointId = packageCheckpointService.getNextCheckpointId(
                tempPackageCheckpoint.getPackages().getId(),
                tempPackageCheckpoint.getCheckpoint().getRoute().getId());
            
            //Enviar paquete a siguiente punto de control
            if(nextCheckpointId != null){
                
                //Obtener punto de control siguiente
                Checkpoint nextChekpoint = checkpointService.getCheckpointById(nextCheckpointId).get();
                
                //Si hay espacio en la cola del punto de control siguiente
                if(nextChekpoint.getPackagesOnQueue() < nextChekpoint.getQueueCapacity()){
                    
                    //Actualizar la tupla actual en package_checkpoint
                    packageCheckpointService.update(packageCheckpoint);
                    
                    //Insertar la nueva tupla en package_checkpoint
                    packageCheckpointService.create(new PackageCheckpoint(nextChekpoint, packageCheckpoint.getPackages(), null, true));
                    
                    //Disminuir en uno packages_on_queue en punto de control actual
                    tempPackageCheckpoint.getCheckpoint().setPackagesOnQueue(tempPackageCheckpoint.getCheckpoint().getPackagesOnQueue()-1);
                    checkpointService.create(tempPackageCheckpoint.getCheckpoint());
                    
                    //Aumentar en uno packages_on_queue en punto de control siguiente
                    nextChekpoint.setPackagesOnQueue(nextChekpoint.getPackagesOnQueue()+1);
                    checkpointService.create(nextChekpoint);
                } 

                //Si la cola del punto de control siguiente esta llena
                else
                    return new ResponseEntity("No se puede procesar el paquete. La cola del siguiente punto de control se encuentra llena.", HttpStatus.BAD_REQUEST);
            } 
            
            //Enviar paquete a destino
            else{
                //Obtener el paquete y actualizarlo
                Package tempPackage = packageService.getById(packageCheckpoint.getPackages().getId());
                tempPackage.setAtDestination(true);
                tempPackage.setOnWay(false);
                tempPackage.setDateEnd(packageCheckpoint.getDate().toLocalDate());
                packageService.update(tempPackage, tempPackage.getId());
                
                //Actualizar la tupla actual en package_checkpoint
                packageCheckpointService.update(packageCheckpoint);
                
                //Disminuir en uno packages_on_queue en punto de control actual
                tempPackageCheckpoint.getCheckpoint().setPackagesOnQueue(tempPackageCheckpoint.getCheckpoint().getPackagesOnQueue()-1);
                checkpointService.create(tempPackageCheckpoint.getCheckpoint());
                
                //Disminuir en uno packages_on_route en la ruta actual
                tempPackageCheckpoint.getCheckpoint().getRoute().setPackagesOnRoute(tempPackageCheckpoint.getCheckpoint().getRoute().getPackagesOnRoute()-1);
                routeService.update(tempPackageCheckpoint.getCheckpoint().getRoute());
            }
           
            queueHandler.verifiyQueue();
            return ResponseEntity.ok().build();
        } catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity("Error en el servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }    
    }
    
    
}
