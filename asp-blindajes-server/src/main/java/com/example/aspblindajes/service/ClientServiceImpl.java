package com.example.aspblindajes.service;

import com.example.aspblindajes.converters.ClientDTOToClient;
import com.example.aspblindajes.dto.ClientDTO;
import com.example.aspblindajes.exception.InvalidArgumentException;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Brand;
import com.example.aspblindajes.model.Client;
import com.example.aspblindajes.model.WorkGroup;
import com.example.aspblindajes.repository.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientDTOToClient clientDTOToClient;

    @Override
    public Client saveClient(ClientDTO clientDTO) throws ResourceAlreadyExistsException, InvalidArgumentException {
        Client client = clientDTOToClient.convert(clientDTO);
        if(clientRepository.findClientByName(clientDTO.getName()).isPresent()){
            log.error("Failed to save client: Client already exists");
            throw new ResourceAlreadyExistsException("Client already exists");
        }
        if(client != null){
            return clientRepository.save(client);
        }
        log.error("Failed to save client: Invalid information provided");
        throw new InvalidArgumentException("Invalid information provided");
    }

    @Override
    public Client updateClient(ClientDTO clientDTO) throws InvalidArgumentException, ResourceNotFoundException {
        if(!clientRepository.existsById(clientDTO.getId())){
            log.error("Failed to update client: The client does not exists");
            throw new ResourceNotFoundException("The client does not exist");
        }
        Client savedClient = clientRepository.save(Objects.requireNonNull(clientDTOToClient.convert(clientDTO)));
        log.info("Updated client successfully");
        return savedClient;
    }

    @Override
    public void deleteClientById(Long id) throws ResourceNotFoundException {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if(clientOptional.isEmpty()){
            log.error("Failed to delete client: Client doesn't exists");
            throw new ResourceNotFoundException("Client does not exists");
        }
        log.info("Deleted client successfully");
        clientRepository.deleteById(id);
    }

    @Override
    public Client findClientById(Long id) throws ResourceNotFoundException {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if(clientOptional.isEmpty()){
            log.error("Failed to find client by ID: The client doesn't exists");
            throw new ResourceNotFoundException("Client does not exist");
        }
        log.info("Client founded by name");
        return clientOptional.get();
    }

    @Override
    public Client findClientByName(String name) throws ResourceNotFoundException {
        Optional<Client> clientOptional = clientRepository.findClientByName(name);
        if(clientOptional.isEmpty()){
            log.error("Failed to find client by name: The client doesn't exists");
            throw new ResourceNotFoundException("The client does not exist");
        }
        log.info("Client founded by name successfully");
        return clientOptional.get();
    }

    @Override
    public List<Client> findAllClients() throws ResourceNotFoundException {
        List<Client> clientList = clientRepository.findAll();
        if (clientList.isEmpty()){
            log.error("Failed to list clients: Client database empty");
            throw new ResourceNotFoundException("There are no clients");
        }
        log.info("All clients founded successfully");
        return clientList;
    }

    @Override
    public Client clientSetHidden(Long id) throws ResourceNotFoundException {
        Optional<Client> clientO = clientRepository.findById(id);
        if(clientO.isPresent()){
            Client client = clientO.get();
            client.setHidden(!client.getHidden());
            clientRepository.save(client);
            log.info("Hidden field changed");
            return client;
        }
        log.error("Client could not be found by name");
        throw new ResourceNotFoundException("Client not found");
    }

    @Override
    public List<Client> listClientHiddenFalse() throws ResourceNotFoundException {
        List<Client> clientList = clientRepository.findAllClientHiddenFalse();
        if(clientList.isEmpty()){
            log.error("There are no unhidden clients");
            throw new ResourceNotFoundException("There are no unhidden clients");
        }
        log.info("All unhidden clients found successfully");
        return clientList;
    }
}
