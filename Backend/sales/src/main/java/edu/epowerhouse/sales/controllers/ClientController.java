package edu.epowerhouse.sales.controllers;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.epowerhouse.common.models.records.Client;
import edu.epowerhouse.sales.services.ClientService;

@RestController
@RequestMapping("/clients")
public class ClientController {
    
    private final ClientService clientService;
    
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    
    @GetMapping("/{nit}")
    public ResponseEntity<Client> getClientByNit(@PathVariable String nit) {
        try {
            Client client = clientService.findClient(nit);
            if (client == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(client);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/")
    public ResponseEntity<Void> createClient(@RequestBody Client client) {
        try {
            clientService.createClient(client);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{nit}")
    public ResponseEntity<Void> updateClient(@PathVariable String nit, @RequestBody Client client) {
        if (!nit.equals(client.nit())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            clientService.updateClient(client);
            return ResponseEntity.ok().build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
