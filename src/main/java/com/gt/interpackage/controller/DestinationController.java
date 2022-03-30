package com.gt.interpackage.controller;

import com.gt.interpackage.model.Destination;
import com.gt.interpackage.service.DestinationService;
import com.gt.interpackage.service.RouteService;
import com.gt.interpackage.source.Constants;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author helmuth
 * @author bryan
 */
@CrossOrigin (origins = Constants.URL_FRONT_END, allowCredentials = "true")
@RestController
@RequestMapping (Constants.API_V1 + "/destination")
public class DestinationController {
    
    @Autowired
    private RouteService routeService;
    
    @Autowired
    private DestinationService destinationService;
    
    @GetMapping("/")
    public ResponseEntity<List<Destination>> getAllDestinations(){
        return ResponseEntity.ok(destinationService.findAll());
    }
    
    
    @PostMapping ("/")
    public ResponseEntity<Destination> addDestination(@RequestBody Destination destination) {
        try { 
            return service(destination, false, null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // 400 Bad Request
        }
    }
    
    /**
     * Metodo que recibe una peticion GET para obtener un destino 
     * en base al id que se recibe como parametro.
     * @param id Id del destino a obtener.
     * @return 
     */
    @CrossOrigin
    @GetMapping(value ="/{id}")
     public ResponseEntity<Optional<Destination>> getDestination(@PathVariable Long id){
        try{
            return ResponseEntity.ok(destinationService.getDestinationById(id));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
     
    /**
     * Metodo que realiza una llamada al servicio de destinos para obtener todos los 
     * destinos cuyo nombre inicie con el nombre que se recibe como parametro.
     * @param name
     * @return Listado de destinos encontrados | Error 400
     */
    @CrossOrigin
    @GetMapping(value ="/search-by-name/{name}")
    public ResponseEntity<List<Destination>> getDestinationByName(@PathVariable String name){
        try{
            return ResponseEntity.ok(destinationService.findByName(name));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    /**
     * Metodo que recibe una peticion GET para obtener un listado paginado de destinos.
     * @param page Numero de pagina actual. Por defecto 1.
     * @param size Tamaño de la pagina. Por defecto 10.
     * @return 
     */
    @CrossOrigin
    @GetMapping("list")
    public ResponseEntity<Page<Destination>> getDestinations(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ){
        try{          
            Page<Destination> destinations = destinationService.getAll(
                PageRequest.of(page, size, Sort.by("name"))
            );
            return new ResponseEntity<Page<Destination>>(destinations, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Metodo que recibe una peticion PATCH para actualizar un destino.
     * Valida que no exista ningun destino registrado que no sea el destino 
     * que se desea actualizar con el nombre recibido y que ese nombre tenga un formato valido. 
     * @param destination
     * @return 
     */ 
     @CrossOrigin
     @PatchMapping
     public ResponseEntity<Destination> updateDestination(@RequestBody Destination destination){
        try{
            if(destination.getName().isBlank() || destination.getName().isEmpty() )
                return new ResponseEntity("Nombre de destino no valido", HttpStatus.BAD_REQUEST);
            
            if(destinationService.exists(destination.getName(), destination.getId()))
                return new ResponseEntity("Nombre de destino ya registrado en el sistema", HttpStatus.BAD_REQUEST);
            
            Destination updatedDestination = destinationService.getDestinationById(destination.getId()).get();
            updatedDestination.setName(destination.getName());
            updatedDestination.setDescription(destination.getDescription());
            updatedDestination.setFee(destination.getFee());
            destinationService.save(updatedDestination);
            return ResponseEntity.ok(updatedDestination);
      } catch(Exception e){
        return new ResponseEntity("Error en el servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
    
    /**
     * Metodo que recibe una peticion DELETE para eliminar el destino cuyo id se 
     * recibe como parametro. Valida que el destino no este asignado a ninguna ruta
     * para poder asi proceder con la eliminacion del destino.
     * @param id
     * @return 
     */
    @Transactional 
    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Destination> deleteDestination(@PathVariable Long id){
        try{
            if(routeService.routeHasDestinationAssigned(id))
                return new ResponseEntity("No se puede eliminar el destino ya que se encuentra asignado a rutas.", HttpStatus.BAD_REQUEST);
            
            Destination tempDestination = destinationService.getDestinationById(id).get();
            destinationService.delete(tempDestination);
            return ResponseEntity.ok().build();
      } catch(Exception e){
            System.out.println(e.getMessage());
        return new ResponseEntity("Error en el servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

        
    public ResponseEntity<Destination> service(Destination destination, boolean update, Long id) throws URISyntaxException, Exception {
        if (destination != null) {
            if (destination.getFee() == null || destination.getName() == null || destination.getName().isEmpty() || destination.getName().isBlank()) {
                return ResponseEntity
                        .badRequest()
                        .header("Error", "Todos los campos son obligatorios.")
                        .build();
            } else {
                if (destination.getFee() < 0) {
                     return ResponseEntity
                        .badRequest()
                        .header("Error", "La tarifa debe de ser mayor a 0.")
                        .build();
                } else {
                    if (update) {
                        // Agregar metodo para el update
                        return null;
                    } else {
                        Destination savedDestination = destinationService.save(destination);
                        return savedDestination == null 
                                ? ResponseEntity
                                        .badRequest()
                                        .header("errorMessage", "Ya exuiste un destino con el nombre: " + destination.getName())
                                        .build()
                                : ResponseEntity
                                .created (
                                        new URI("/destination/" + savedDestination.getId()))
                                .body(savedDestination);
                    }
                }
            }
        } 
        return ResponseEntity.badRequest().build();
    }
}
