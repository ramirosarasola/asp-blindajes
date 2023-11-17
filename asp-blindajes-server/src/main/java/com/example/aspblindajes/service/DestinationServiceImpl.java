package com.example.aspblindajes.service;

import com.example.aspblindajes.converters.DestinationDTOToDestination;
import com.example.aspblindajes.dto.DestinationDTO;
import com.example.aspblindajes.exception.InvalidArgumentException;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Client;
import com.example.aspblindajes.model.Destination;
import com.example.aspblindajes.repository.DestinationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class DestinationServiceImpl implements DestinationService{
    private final DestinationRepository destinationRepository;
    private final DestinationDTOToDestination converter;
    @Override
    public Destination saveDestination(DestinationDTO destinationDTO) throws ResourceAlreadyExistsException, InvalidArgumentException {
        Destination destination = converter.convert(destinationDTO);
        if(destinationRepository.findDestinationByName(destinationDTO.getName()).isPresent()){
            log.error("Failed to save destination: Destination already exists");
            throw new ResourceAlreadyExistsException("Destination already exists");
        }
        if(destination != null){
            return destinationRepository.save(destination);
        }
        log.error("Failed to save destination: Invalid information provided");
        throw new InvalidArgumentException("Invalid information provided");
    }

    @Override
    public Destination updateDestination(DestinationDTO destinationDTO) throws InvalidArgumentException, ResourceNotFoundException {
        if(!destinationRepository.existsById(destinationDTO.getId())){
            log.error("Failed to update destination: The destination does not exists");
            throw new ResourceNotFoundException("The destination does not exist");
        }
        Destination savedDestination = destinationRepository.save(Objects.requireNonNull(converter.convert(destinationDTO)));
        log.info("Updated destination successfully");
        return savedDestination;
    }

    @Override
    public void deleteDestinationById(Long id) throws ResourceNotFoundException {
        Optional<Destination> destinationOptional = destinationRepository.findById(id);
        if(destinationOptional.isEmpty()){
            log.error("Failed to delete destination: Destination doesn't exists");
            throw new ResourceNotFoundException("Destination does not exists");
        }
        log.info("Deleted destination successfully");
        destinationRepository.deleteById(id);
    }

    @Override
    public Destination findDestinationById(Long id) throws ResourceNotFoundException {
        Optional<Destination> destinationOptional = destinationRepository.findById(id);
        if(destinationOptional.isEmpty()){
            log.error("Failed to find destination by ID: The destination doesn't exists");
            throw new ResourceNotFoundException("Destination does not exist");
        }
        log.info("Destination founded by name");
        return destinationOptional.get();
    }

    @Override
    public Destination findDestinationByName(String name) throws ResourceNotFoundException {
        Optional<Destination> destinationOptional = destinationRepository.findDestinationByName(name);
        if(destinationOptional.isEmpty()){
            log.error("Failed to find destination by name: The destination doesn't exists");
            throw new ResourceNotFoundException("The destination does not exist");
        }
        log.info("Destination founded by name successfully");
        return destinationOptional.get();
    }

    @Override
    public List<Destination> findAllDestinations() throws ResourceNotFoundException {
        List<Destination> destinationList = destinationRepository.findAll();
        if (destinationList.isEmpty()){
            log.error("Failed to list destinations: Destination database empty");
            throw new ResourceNotFoundException("There are no destinations");
        }
        log.info("All destinations founded successfully");
        return destinationList;
    }

    @Override
    public Destination destinationSetHidden(Long id) throws ResourceNotFoundException {
        Optional<Destination> destinationO = destinationRepository.findById(id);
        if(destinationO.isPresent()){
            Destination destination = destinationO.get();
            destination.setHidden(!destination.getHidden());
            destinationRepository.save(destination);
            log.info("Hidden field changed");
            return destination;
        }
        log.error("Destination could not be found by name");
        throw new ResourceNotFoundException("Destination not found");
    }

    @Override
    public List<Destination> listDestinationHiddenFalse() throws ResourceNotFoundException {
        List<Destination> destinationList = destinationRepository.findAllDestinationHiddenFalse();
        if(destinationList.isEmpty()){
            log.error("There are no unhidden destinations");
            throw new ResourceNotFoundException("There are no unhidden destinations");
        }
        log.info("All unhidden destinations found successfully");
        return destinationList;
    }
}
