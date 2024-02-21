package com.example.aspblindajes.controller;
import com.example.aspblindajes.dto.AllMonthlyProductivityResponse;
import com.example.aspblindajes.dto.MonthlyProductivityResponse;
import com.example.aspblindajes.dto.VehicleDTO;
import com.example.aspblindajes.dto.VehiclesPerAreaQueryResponse;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Vehicle;
import com.example.aspblindajes.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<Vehicle> saveVehicle (@RequestBody VehicleDTO vehicleDTO, @RequestParam (value = "userName") String userName) throws ResourceAlreadyExistsException {
        return ResponseEntity.ok(vehicleService.saveVehicle(vehicleDTO, userName));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Vehicle>> findAllVehicles () throws ResourceNotFoundException {
            return ResponseEntity.ok(vehicleService.findAllVehicles());
    }

    @GetMapping
    public ResponseEntity<VehicleDTO> findVehicleById (@RequestParam (value = "chasis") String id) throws ResourceNotFoundException {
        return ResponseEntity.ok(vehicleService.findVehicleById(id));
    }

    @GetMapping("/getVehiclesPerArea")
    public ResponseEntity<List<VehiclesPerAreaQueryResponse>> getVehiclesPerArea () {
        return ResponseEntity.ok(vehicleService.getAmoutOfVehiclesPerArea());
    }

    @GetMapping("/monthlyProductivity")
    public ResponseEntity<MonthlyProductivityResponse> getMonthlyProductivity (){
        return ResponseEntity.ok(vehicleService.monthlyProductivity());
    }

    @GetMapping("/weeklyProductivity")
    public ResponseEntity<MonthlyProductivityResponse> getWeeklyProductivity (){
        return ResponseEntity.ok(vehicleService.weeklyProductivity());
    }

    @GetMapping("/allMonthlyProductivity")
    public ResponseEntity<List<AllMonthlyProductivityResponse>> getAllMonthlyProductivity (@RequestParam (value = "year") int year){
        return ResponseEntity.ok(vehicleService.allMonthlyProductivity(year));
    }
    @GetMapping("filters")
    public ResponseEntity<Page<VehicleDTO>> getVehiclesByFilter (
            @RequestParam(value = "purchaseOrder", required = false) String purchaseOrder,
            @RequestParam(value = "clientName", required = false) String clientName,
            @RequestParam(value = "modelName", required = false) String modelName,
            @RequestParam(value = "areaName", required = false) String areaName,
            @RequestParam(value = "chasis", required = false) String chasis,
            @RequestParam(value = "finished", required = false) Boolean finished,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "30", required = false) int size) {
        // Lógica para obtener la lista paginada
        Page<VehicleDTO> vehiclePage = vehicleService.getVehiclesByFilter(
                clientName, purchaseOrder, areaName, modelName, chasis, finished, PageRequest.of(page, size));
        return ResponseEntity.ok(vehiclePage);
    }



    @DeleteMapping
    public ResponseEntity<String> deleteVehicleByChasis (@RequestParam (value = "id") String id) throws ResourceNotFoundException {
        vehicleService.deleteVehicleById(id);
        return ResponseEntity.ok("The vehicle with id number " + id + " has been deleted");
    }

    @PutMapping
    public ResponseEntity<Vehicle> updateVehicle (@RequestBody VehicleDTO vehicleDTO) throws ResourceNotFoundException {
        return ResponseEntity.ok(vehicleService.updateVehicle(vehicleDTO));
    }



}
