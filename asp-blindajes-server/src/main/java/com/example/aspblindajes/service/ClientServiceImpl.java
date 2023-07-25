package com.example.aspblindajes.service;

import com.example.aspblindajes.converters.ClientDTOToClient;
import com.example.aspblindajes.dto.ClientDTO;
import com.example.aspblindajes.exception.InvalidArgumentException;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Client;
import com.example.aspblindajes.repository.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
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
        throw new InvalidArgumentException("Invalid information provided");
    }

    @Override
    public Client updateClient(ClientDTO clientDTO) throws InvalidArgumentException, ResourceNotFoundException {
        Optional<Client> clientOptional = clientRepository.findById(clientDTO.getId());
        if(clientOptional.isEmpty()){
            throw new ResourceNotFoundException("The client does not exist");
        }
        log.info("Saving client...");
        return clientRepository.save(clientOptional.get());
    }

    @Override
    public void deleteClientById(Long id) throws ResourceNotFoundException {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if(clientOptional.isEmpty()){
            throw new ResourceNotFoundException("Client does not exist");
        }
        log.info("Deleting client...");
        clientRepository.deleteById(id);
    }

    @Override
    public Client findClientById(Long id) throws ResourceNotFoundException {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if(clientOptional.isEmpty()){
            throw new ResourceNotFoundException("Client does not exist");
        }
        return clientOptional.get();
    }

    @Override
    public Client findClientByName(String name) throws ResourceNotFoundException {
        Optional<Client> clientOptional = clientRepository.findClientByName(name);
        if(clientOptional.isEmpty()){
            throw new ResourceNotFoundException("The client does not exist");
        }
        return clientOptional.get();
    }

    @Override
    public List<Client> findAllClients() throws ResourceNotFoundException {
        List<Client> clientList = clientRepository.findAll();
        if (clientList.isEmpty()){
            log.info("Client database empty");
            throw new ResourceNotFoundException("There are no clients");
        }
        return clientList;
    }
}
