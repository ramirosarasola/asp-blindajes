package com.example.aspblindajes.controller;

import com.example.aspblindajes.dto.DestinationDTO;
import com.example.aspblindajes.exception.InvalidArgumentException;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Destination;
import com.example.aspblindajes.service.DestinationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/destination")
public class DestinationController {
    private final DestinationService destinationService;
    @PostMapping
    public ResponseEntity<Destination> saveDestination(@RequestBody DestinationDTO destinationDTO) throws InvalidArgumentException, ResourceAlreadyExistsException {
        return ResponseEntity.ok(destinationService.saveDestination(destinationDTO));
    }
    @GetMapping
    public ResponseEntity<Destination> findDestinationById(@RequestParam(value = "id") Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(destinationService.findDestinationById(id));
    }
    @GetMapping("/all")
    public ResponseEntity<List<Destination>> findAllDestinations() throws ResourceNotFoundException {
        return ResponseEntity.ok(destinationService.findAllDestinations());
    }
    @GetMapping("/visible")
    ResponseEntity<List<Destination>> listDestinationHiddenFalse () throws ResourceNotFoundException {
        return ResponseEntity.ok(destinationService.listDestinationHiddenFalse());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteDestinationById (@RequestParam(value = "id") Long id)throws ResourceNotFoundException{
        destinationService.deleteDestinationById(id);
        return ResponseEntity.ok("The destination with id " + id + " has been deleted");
    }

    @PutMapping("/hide")
    ResponseEntity<Destination> setHiddenDestination(@RequestParam(value = "id") Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(destinationService.destinationSetHidden(id));
    }

    @PutMapping
    public ResponseEntity<Destination> updateDestination(@RequestBody DestinationDTO destinationDTO) throws ResourceNotFoundException, InvalidArgumentException {
        return ResponseEntity.ok( destinationService.updateDestination(destinationDTO));
    }

}
