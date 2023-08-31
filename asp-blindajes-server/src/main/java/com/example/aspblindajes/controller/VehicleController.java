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

    @DeleteMapping
    public ResponseEntity<String> deleteVehicleByChasis (@RequestParam (value = "chasis") String chasis) throws ResourceNotFoundException {
        vehicleService.deleteVehicleByChasis(chasis);
        return ResponseEntity.ok("The vehicle with chasis number " + chasis + " has been deleted");
    }

    @PutMapping
    public ResponseEntity<Vehicle> updateVehicle (@RequestBody VehicleDTO vehicleDTO) throws ResourceNotFoundException {
        return ResponseEntity.ok(vehicleService.updateVehicle(vehicleDTO));
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
    public ResponseEntity<List<VehicleDTO>> getVehiclesByFilter (@RequestParam (value = "purchaseOrder", required = false) String purchaseOrder,
                                                                 @RequestParam (value = "clientName" , required = false) String clientName,
                                                                 @RequestParam (value = "modelName" , required = false) String modelName,
                                                                 @RequestParam (value = "areaName" , required = false) String areaName,
                                                                 @RequestParam (value = "chasis" , required = false) String chasis){
        return ResponseEntity.ok(vehicleService.getVehiclesByFilter(purchaseOrder,clientName, modelName, areaName , chasis));
    }



}
