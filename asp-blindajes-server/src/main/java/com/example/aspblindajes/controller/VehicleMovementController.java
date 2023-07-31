package com.example.aspblindajes.controller;

import com.example.aspblindajes.model.VehicleMovement;
import com.example.aspblindajes.service.VehicleMovementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/vehicleMovement")
@AllArgsConstructor
public class VehicleMovementController {

    private final VehicleMovementService vehicleMovementService;

    @PostMapping
    public ResponseEntity<VehicleMovement> saveVehicleMovement (@RequestParam (value = "chasis") String chasis) throws Exception {
        return ResponseEntity.ok(vehicleMovementService.saveVehicleMovement(chasis));
    }

}
