package com.example.aspblindajes.service;

import com.example.aspblindajes.dto.DestinationDTO;
import com.example.aspblindajes.exception.InvalidArgumentException;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Destination;

import java.util.List;

public interface DestinationService {
    Destination saveDestination(DestinationDTO destinationDTO) throws ResourceAlreadyExistsException, InvalidArgumentException;
    Destination updateDestination(DestinationDTO destinationDTO) throws InvalidArgumentException, ResourceNotFoundException;
    void deleteDestinationById(Long id) throws ResourceNotFoundException;
    Destination findDestinationById(Long id) throws ResourceNotFoundException;
    Destination findDestinationByName(String name) throws ResourceNotFoundException;
    List<Destination> findAllDestinations() throws ResourceNotFoundException;
    Destination destinationSetHidden (Long id) throws ResourceNotFoundException;
    List<Destination> listDestinationHiddenFalse () throws ResourceNotFoundException;
}
