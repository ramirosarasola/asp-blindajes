package com.example.aspblindajes.service;

import com.example.aspblindajes.dto.ClientDTO;
import com.example.aspblindajes.exception.InvalidArgumentException;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Client;

import java.util.List;

public interface ClientService {
    Client saveClient(ClientDTO clientDTO) throws ResourceAlreadyExistsException, InvalidArgumentException;
    Client updateClient(ClientDTO clientDTO) throws InvalidArgumentException, ResourceNotFoundException;
    void deleteClientById(Long id) throws ResourceNotFoundException;
    Client findClientById(Long id) throws ResourceNotFoundException;
    Client findClientByName(String name) throws ResourceNotFoundException;
    List<Client> findAllClients() throws ResourceNotFoundException;
}
