package com.example.aspblindajes.controller;
import com.example.aspblindajes.dto.VehicleQualityControlDTO;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.VehicleQualityControl;
import com.example.aspblindajes.service.VehicleQualityControlService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/vehicleQualityControl")
public class VehicleQualityControlController {
    private final VehicleQualityControlService vehicleQualityControlService;

    @PostMapping
    public ResponseEntity<VehicleQualityControl> saveVehicleQualityControl(@RequestBody VehicleQualityControlDTO vehicleQualityControlDTO) throws ResourceAlreadyExistsException {
        return ResponseEntity.ok(vehicleQualityControlService.saveVehicleQualityControl(vehicleQualityControlDTO));
    }


    @GetMapping("/all")
    public ResponseEntity<List<VehicleQualityControl>> findAllVehicleQualityControl () throws ResourceNotFoundException {
        return ResponseEntity.ok(vehicleQualityControlService.findAllVehicleQualityControl());
    }

    @GetMapping
    public ResponseEntity<VehicleQualityControl> findVehicleQualityControlById (@RequestParam (value = "id") Long id) throws ResourceNotFoundException{
        return ResponseEntity.ok(vehicleQualityControlService.findVehicleQualityControlById(id));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteVehicleQualityControlById(@RequestParam(value = "id") Long id) throws ResourceNotFoundException{
        vehicleQualityControlService.deleteVehicleQualityControlById(id);
        return ResponseEntity.ok("Vehicle quality control deleted successfully");
    }


    @PutMapping
    public ResponseEntity<VehicleQualityControl> updateVehicleQualityControl (@RequestBody VehicleQualityControlDTO vehicleQualityControlDTO) throws ResourceNotFoundException{
        return ResponseEntity.ok(vehicleQualityControlService.updateVehicleQualityControl(vehicleQualityControlDTO));
    }

    @GetMapping("/vehicleId")
    public ResponseEntity<List<VehicleQualityControl>> findVehicleQualityControlByVehicleId(@RequestParam(value = "id") String id) throws ResourceNotFoundException{
        return ResponseEntity.ok(vehicleQualityControlService.findVehicleQualityControlByVehicleId(id));
    }
}
