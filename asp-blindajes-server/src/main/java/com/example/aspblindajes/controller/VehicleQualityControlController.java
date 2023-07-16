package com.example.aspblindajes.controller;
import com.example.aspblindajes.service.VehicleQualityControlService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/vehicleQualityControlller")
public class VehicleQualityControlController {
    private final VehicleQualityControlService vehicleQualityControlService;

//    @PostMapping
//    public ResponseEntity<VehicleQualityControl> saveVehicleQualityControl(@RequestBody VehicleQualityControl vehicleQualityControl){
//        return ResponseEntity.ok(vehicleQualityControlService.)
//    }
}
