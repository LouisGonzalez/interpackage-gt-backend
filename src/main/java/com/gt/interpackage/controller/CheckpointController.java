package com.gt.interpackage.controller;

import com.gt.interpackage.model.Checkpoint;
import com.gt.interpackage.service.CheckpointService;
import com.gt.interpackage.service.EmployeeService;
import com.gt.interpackage.service.RouteService;
import com.gt.interpackage.source.Constants;
import com.gt.interpackage.utils.RequestType;
import com.gt.interpackage.model.Employee;
import com.gt.interpackage.service.EmployeeTypeService;
import com.gt.interpackage.service.PackageCheckpointService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author helmuth
 */
@RestController
@RequestMapping(Constants.API_V1 + "/checkpoint")
public class CheckpointController {
    
    @Autowired
    private CheckpointService checkpointService;
    
    @Autowired
    private RouteService routeService;
    
    @Autowired 
    private EmployeeService employeeService;
    
    @Autowired
    private EmployeeTypeService employeeTypeService;
    
    @Autowired
    private PackageCheckpointService packageCheckpointService;
    
    /**
     * Metodo que recibe una peticion POST para la creacion de un punto
     * de control. Llama al metodo execute para realizar validaciones y
     * ejecutar la creacion del punto de control. 
     * @param checkpoint Punto de control a crear.
     * @return 
     */
    @CrossOrigin
    @PostMapping
    public ResponseEntity<Checkpoint> createCheckpoint(@RequestBody Checkpoint checkpoint){
        return execute(checkpoint, RequestType.SAVE, null, false);
    }
    
    /**
     * Metodo que recibe una peticion GET para obtener un listado paginado de puntos de control.
     * @param page Numero de pagina actual. Por defecto 1.
     * @param size Tamaño de la pagina. Por defecto 10.
     * @return 
     */
    @CrossOrigin
    @GetMapping("list")
    public ResponseEntity<Page<Checkpoint>> getCheckpoints(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ){
        try{          
            Page<Checkpoint> checkpoints = checkpointService.getAll(
                    PageRequest.of(page, size, Sort.by("routeId"))
            );
            return new ResponseEntity<>(checkpoints, HttpStatus.OK);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity("Error en el servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @CrossOrigin
    @GetMapping("list/{cui}")
    public ResponseEntity<List<Checkpoint>> getAllByAssignedOperator(@PathVariable Long cui){
        try{  
            return ResponseEntity.ok(checkpointService.getAllByAssignedOperator(cui));
        } catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity("Error en el servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
     /**
     * Metodo que recibe una peticion DELETE para eliminar el punto de control cuyo id se 
     * recibe como parametro. Valida que el punto de control no tenga paquetes en cola, que
     * exista y que no se haya procesado ni un solo paquete en ese punto de control.
     * @param id
     * @return 
     */
    @Transactional 
    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Checkpoint> deleteCheckpoint(@PathVariable Long id){
        try{
            Checkpoint tempCheckpoint = checkpointService.getCheckpointById(id).get();
      
            if(tempCheckpoint == null)
                return new ResponseEntity("El punto de control que se desea eliminar no existe en el sistema.", HttpStatus.BAD_REQUEST);
            
            if(tempCheckpoint.getPackagesOnQueue() > 0)
                return new ResponseEntity("No se puede eliminar un punto de control que contiene paquetes en cola.", HttpStatus.BAD_REQUEST);
            
            if(packageCheckpointService.existsAnyRegisterOfCheckpointById(id))
                return new ResponseEntity("No se puede eliminar el punto de control debido a que se han registrado paquetes en el mismo anteriormente. Consulte al DBA.", HttpStatus.BAD_REQUEST);
            
            checkpointService.delete(tempCheckpoint);
            return ResponseEntity.ok().build();
      } catch(Exception e){
        return new ResponseEntity("Error en el servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
    
    /**
     * Metodo que recibe una peticion GET para obtener un punto de control
     * en base al id que se recibe como parametro.
     * @param id Id del destino a obtener.
     * @return 
     */
    @CrossOrigin
    @GetMapping(value ="/{id}")
    public ResponseEntity<Checkpoint> getCheckpoint(@PathVariable Long id){
        try{
            Checkpoint checkpoint = checkpointService.getCheckpointById(id).get();
            return checkpoint != null 
                    ? ResponseEntity.ok(checkpoint)
                    : ResponseEntity
                            .notFound()
                            .build();
        } catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @CrossOrigin
    @PutMapping (value ="update/{id}")
    public ResponseEntity<Checkpoint> updateCheckpoint(@RequestBody Checkpoint update, @PathVariable Long id) {
        Checkpoint checkpoint = checkpointService.getCheckpointById(id).get();
        if (checkpoint == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        update.setActive(checkpoint.getActive());
        update.setAssignedOperator(checkpoint.getAssignedOperator());
        update.setRoute(checkpoint.getRoute());
        return execute(update, RequestType.UPDATE, id, update.getDescription().equalsIgnoreCase(checkpoint.getDescription()));
    }
     
    @CrossOrigin
    @PutMapping (value ="operator/{id}")
    public ResponseEntity<Checkpoint> updateAssignamentOperatorCheckpoint(@RequestBody Checkpoint update, @PathVariable Long id) {
        Checkpoint checkpoint = checkpointService.getCheckpointById(id).get();
        if (checkpoint == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        Employee operator;
        try {
            operator = employeeService.getByCUI(update.getAssignedOperator().getCUI());
            if (operator == null) {
                 return new ResponseEntity ("El operador seleccionado no existe en el sistema.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
        update.setAssignedOperator(operator);
        update.setActive(checkpoint.getActive());
        update.setRoute(checkpoint.getRoute());
        return execute(update, RequestType.UPDATE, id, update.getDescription().equalsIgnoreCase(checkpoint.getDescription()));
    }
    
    private ResponseEntity<Checkpoint> execute(Checkpoint checkpoint, RequestType type, Long id, boolean check){
        try{
            if(checkpoint.getDescription() == null || checkpoint.getQueueCapacity() == null || checkpoint.getAssignedOperator().getCUI() == null
            || checkpoint.getActive() == null || checkpoint.getRoute().getId() == null || checkpoint.getOperationFee() == null)
                return new ResponseEntity("Todos los campos son obligatorios.", HttpStatus.BAD_REQUEST);
            
            if(checkpoint.getDescription().isEmpty() || checkpoint.getDescription().isBlank())
                return new ResponseEntity("Nombre de punto de control no valido.", HttpStatus.BAD_REQUEST);
            
            
            if(!check && checkpointService.routeAlreadyHasACheckpointWithName(checkpoint.getRoute().getId(), checkpoint.getDescription()))
                return new ResponseEntity("Nombre de punto de control ya registrado en la ruta seleccionada.", HttpStatus.BAD_REQUEST);
            
            if(!routeService.existsById(checkpoint.getRoute().getId()))
                return new ResponseEntity("La ruta seleccionada no existe en el sistema.", HttpStatus.BAD_REQUEST);

            
            Employee employee = employeeService.getByCUI(checkpoint.getAssignedOperator().getCUI());
            
            if(employee == null)
                return new ResponseEntity("El operador seleccionada no existe en el sistema.", HttpStatus.BAD_REQUEST);

            if(employeeTypeService.getEmployeeTypeByName("operator").getId() != Long.parseLong(employee.getType().toString()))
              return new ResponseEntity("El empleado seleccionada no es un operador.", HttpStatus.BAD_REQUEST);
            
            if(type == RequestType.SAVE){
                Checkpoint tempCheckpoint = checkpointService.create(checkpoint);
                return ResponseEntity.created(new URI("/checkpoint/"+tempCheckpoint.getId())).body(tempCheckpoint);
            } else if (type == RequestType.UPDATE){
                Checkpoint tempCheckpoint = checkpointService.create(checkpoint);
                return  ResponseEntity.ok(tempCheckpoint);
            }
                
            //Agregar la parte de actualizacion y retornar el valor correspodiente
            return null;
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity("Error en el servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
