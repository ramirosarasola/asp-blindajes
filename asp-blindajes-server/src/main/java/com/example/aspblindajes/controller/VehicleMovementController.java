package com.example.aspblindajes.controller;

import com.example.aspblindajes.dto.VehicleMovementDTO;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.VehicleMovement;
import com.example.aspblindajes.service.VehicleMovementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/vehicleMovement")
@AllArgsConstructor
public class VehicleMovementController {

    private final VehicleMovementService vehicleMovementService;

    @PostMapping
    public ResponseEntity<VehicleMovement> saveVehicleMovement (@RequestParam (value = "chasis") String chasis, @RequestParam (value = "userName") String userName) throws Exception {
        return ResponseEntity.ok(vehicleMovementService.saveVehicleMovement(chasis, userName));
    }

    @GetMapping
    public ResponseEntity<VehicleMovementDTO> findVehicleMovementById (@RequestParam (value = "id") Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(vehicleMovementService.findVehicleMovementById(id));
    }

    @GetMapping ("/all")
    public ResponseEntity<List<VehicleMovementDTO>> findAllVehicleMovements () throws ResourceNotFoundException {
        return ResponseEntity.ok(vehicleMovementService.findAllVehicleMovements());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteVehicleMovementById(Long id) throws ResourceNotFoundException {
        vehicleMovementService.deleteVehicleMovementById(id);
        return ResponseEntity.ok("The vehicle movement has been deleted succesfully");
    }

    @GetMapping("/chasis")
    public ResponseEntity<List<VehicleMovementDTO>> findVehicleMovementsByChasis (String chasis) throws ResourceNotFoundException {
        return ResponseEntity.ok(vehicleMovementService.findVehicleMovementsByChasis(chasis));
    }
}
