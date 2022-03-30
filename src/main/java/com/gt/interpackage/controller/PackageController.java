package com.gt.interpackage.controller;

import com.gt.interpackage.service.PackageService;
import com.gt.interpackage.source.Constants;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gt.interpackage.model.Package;
import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
/**
 *
 * @author Luis
 */
@CrossOrigin (origins = Constants.URL_FRONT_END, allowCredentials = "true")
@RestController
@RequestMapping (Constants.API_V1 + "/package")
public class PackageController {
    
    @Autowired
    private PackageService _packageService;
    
    /*
     * Metodo que recibe una peticion GET para obtener el listado de
     * paquetes en destino y que aun no han sido entregados
    */
    @GetMapping("/in-destination/")
    public ResponseEntity<Page<Package>> getInDestination(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ){
        try{          
            Page<Package> packages = _packageService.getAllAtDestination(
                PageRequest.of(page, size));
            return new ResponseEntity<>(packages, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
     /*
     * Metodo que recibe una peticion GET para obtener el listado de
     * paquetes en ruta.
    */
    @GetMapping("/on-route")
    public ResponseEntity<Page<Package>> getOnRoute(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ){
        try{          
            Page<Package> packages = _packageService.getAllOnRoute(
                PageRequest.of(page, size));
            return new ResponseEntity<>(packages, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /*
     * Metodo que recibe una peticion POST para la creacion de un paquete.
     * @param package Package a crear
    */
    @CrossOrigin
    @PostMapping("/")
    public ResponseEntity<Package> addPackage(@RequestBody Package pack){
        try {
            Package tempPackage = _packageService.addPackage(pack);
            return ResponseEntity.created(new URI("/package/"+tempPackage.getId())).body(tempPackage);
        } catch(Exception e){
            System.out.println(e);
            return new ResponseEntity("Error en el servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    /*
     * Metodo que recibe una peticion PUT para actualizar una ruta
    */
    @PutMapping("/{id}")
    public ResponseEntity<Package> updatePackage(@RequestBody Package packUpdate, @PathVariable Long id){
        try {
            Package pack = _packageService.update(packUpdate, id);
            return pack != null ?
                    ResponseEntity.ok(pack) :
                    ResponseEntity
                        .notFound()
                        .header("Error", "No se encuentra el paquete con id "+id)
                        .build();
        } catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("trace-by-invoice/{id}")
    public ResponseEntity<List<Package>> getPackagesOnRouteByInvoiceId(@PathVariable Long id){
        try{
            return ResponseEntity.ok(_packageService.getPackagesOnRouteByInoviceId(id));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    

    @GetMapping("/invoice/{id_invoice}")
    public ResponseEntity<List<Package>> getPackagesByInvoice(@PathVariable Long id_invoice){
        return ResponseEntity.ok(_packageService.getPackagesByInvoice(id_invoice));
    }
    
}
