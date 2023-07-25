package com.example.aspblindajes.controller;

import com.example.aspblindajes.converters.ClientDTOToClient;
import com.example.aspblindajes.dto.ClientDTO;
import com.example.aspblindajes.exception.InvalidArgumentException;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Client;
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
}
