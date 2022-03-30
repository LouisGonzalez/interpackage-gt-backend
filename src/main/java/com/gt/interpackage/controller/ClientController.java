/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.controller;

import com.gt.interpackage.model.Client;
import com.gt.interpackage.repository.EmployeeRepository;
import com.gt.interpackage.request.ClientReport;
import com.gt.interpackage.service.ClientService;
import com.gt.interpackage.service.EmployeeService;
import com.gt.interpackage.source.Constants;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author Luis
 */
@CrossOrigin (origins = Constants.URL_FRONT_END, allowCredentials = "true")
@RestController
@RequestMapping (Constants.API_V1 + "/client")
public class ClientController {
    
    @Autowired
    private ClientService _clientService;
    
    @GetMapping ("/")
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(_clientService.findAll());
    }
    
    @GetMapping ("/{nit}")
    public ResponseEntity<Client> getClientByNit(@PathVariable Integer nit){
        try{
            Client client = _clientService.getByNit(nit);
            return client != null ?
                    ResponseEntity.ok(client) :
                    ResponseEntity
                        .notFound()
                        .header("Error", "No se encuentra registrado un cliente con el nit "+nit)
                        .build();
        } catch(Exception ex){
            return ResponseEntity.internalServerError().build();    //505 Internal Server Error
        }
    }
    
    @PostMapping ("/")
    public ResponseEntity<Client> addClient(@RequestBody Client client){
        try {
            if(_clientService.existsByCui(client.getCui()))
                return new ResponseEntity("El cliente con el id: "+client.getCui()+" ya existe", HttpStatus.INTERNAL_SERVER_ERROR);
            if(_clientService.existsByNit(client.getNit()))
                return new ResponseEntity("El cliente con el numero de NIT: "+client.getNit()+" ya existe", HttpStatus.INTERNAL_SERVER_ERROR);
            Client savedClient = _clientService.save(client);
            return ResponseEntity.created(
                    new URI("/client/" + savedClient.getCui()))
                    .body(savedClient);
        } catch(Exception e){
            return ResponseEntity.badRequest().build(); //400 Bad Request
        }
    }
    
    /*      REPORT      */
    
    @GetMapping("/report-3/")
    public ResponseEntity<List<ClientReport>> getClientReport(){
        System.out.println("Entro aqui");
        return ResponseEntity.ok(_clientService.getClientReport());
    }
}
