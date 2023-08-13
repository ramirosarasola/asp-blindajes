package com.example.aspblindajes.controller;
import com.example.aspblindajes.dto.VehicleDTO;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Vehicle;
import com.example.aspblindajes.service.VehicleService;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.ConverterRegistration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<Vehicle> saveVehicle (@RequestBody VehicleDTO vehicleDTO) {
        return ResponseEntity.ok(vehicleService.saveVehicle(vehicleDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Vehicle>> findAllVehicles () throws ResourceNotFoundException {
            return ResponseEntity.ok(vehicleService.findAllVehicles());
    }

    @GetMapping
    public ResponseEntity<Vehicle> findVehicleById (@RequestParam (value = "chasis") String chasis) throws ResourceNotFoundException {
        return ResponseEntity.ok(vehicleService.findVehicleById(chasis));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteVehicleById (@RequestParam (value = "chasis") String chasis) throws ResourceNotFoundException {
        vehicleService.deleteVehicleById(chasis);
        return ResponseEntity.ok("The vehicle with chasis number " + chasis + " has been deleted");
    }

    @PutMapping
    public ResponseEntity<Vehicle> updateVehicle (@RequestBody VehicleDTO vehicleDTO) throws ResourceNotFoundException {
        return ResponseEntity.ok(vehicleService.updateVehicle(vehicleDTO));
    }



}
