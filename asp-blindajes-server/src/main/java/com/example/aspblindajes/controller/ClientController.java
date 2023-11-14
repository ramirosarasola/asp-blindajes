package com.example.aspblindajes.controller;

import com.example.aspblindajes.converters.ClientDTOToClient;
import com.example.aspblindajes.dto.BrandModelDTO;
import com.example.aspblindajes.dto.ClientDTO;
import com.example.aspblindajes.exception.InvalidArgumentException;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Brand;
import com.example.aspblindajes.model.BrandModel;
import com.example.aspblindajes.model.Client;
import com.example.aspblindajes.model.WorkGroup;
import com.example.aspblindajes.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;
    @PostMapping
    public ResponseEntity<Client> saveClient(@RequestBody ClientDTO clientDTO) throws InvalidArgumentException, ResourceAlreadyExistsException {
        return ResponseEntity.ok(clientService.saveClient(clientDTO));
    }
    @GetMapping
    public ResponseEntity<Client> findClientById(@RequestParam(value = "id") Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(clientService.findClientById(id));
    }
    @GetMapping("/all")
    public ResponseEntity<List<Client>> findAllClients() throws ResourceNotFoundException {
        return ResponseEntity.ok(clientService.findAllClients());
    }
    @GetMapping("/visible")
    ResponseEntity<List<Client>> listClientHiddenFalse () throws ResourceNotFoundException {
        return ResponseEntity.ok(clientService.listClientHiddenFalse());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteClientById (@RequestParam(value = "id") Long id)throws ResourceNotFoundException{
        clientService.deleteClientById(id);
        return ResponseEntity.ok("The client with id " + id + " has been deleted");
    }

    @PutMapping("/hide")
    ResponseEntity<Client> setHiddenClient(@RequestParam(value = "id") Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(clientService.clientSetHidden(id));
    }

    @PutMapping
    public ResponseEntity<Client> updateClient(@RequestBody ClientDTO clientDTO) throws ResourceNotFoundException, InvalidArgumentException {
        return ResponseEntity.ok( clientService.updateClient(clientDTO));
    }


}
