package com.example.aspblindajes.service;

import com.example.aspblindajes.converters.VehicleMovementeToVehicleMovementDTO;
import com.example.aspblindajes.dto.VehicleMovementDTO;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Area;
import com.example.aspblindajes.model.MovementType;
import com.example.aspblindajes.model.Vehicle;
import com.example.aspblindajes.model.VehicleMovement;
import com.example.aspblindajes.repository.VehicleMovementRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class VehicleMovementServiceImpl implements VehicleMovementService{
    private final VehicleMovementRepository vehicleMovementRepository;
    private final VehicleService vehicleService;
    private final VehicleMovementeToVehicleMovementDTO vehicleMovementeToVehicleMovementDTO;


    @Override
    public VehicleMovement saveVehicleMovement(String chasis) throws Exception {
        Vehicle vehicle = vehicleService.findVehicleById(chasis);
        Area area =  vehicle.getArea();
        if(area.name().equals("DELIVERED")){
            log.error("Failed to save a new vehicle movement: The vehicle has already been delivered");
            throw new Exception("Vehicle has already been delivered"); // todo -> create personalized exceptions
        }
        boolean bool = false;
        if ( vehicle.getQualityControlList().size() > 0){
            bool =  vehicle.getQualityControlList().get(vehicle.getQualityControlList().size()-1).getCanBeCheckedOut();
        }
        if (area.name().equals("PRODUCTION") && !bool){
            log.error("Failed to save a new vehicle movement: The vehicle cannot be checked out from production until QC is done");
            throw new Exception("The vehicle cannot be checked out from production until QC is done"); // todo -> create personalized exceptions
        }
        VehicleMovement vehicleMovement = new VehicleMovement();
        vehicleMovement.setVehicle(vehicle);
        vehicleMovement.setMovementType(movementTypeHandler(area, bool, vehicle.getChasis()));
        log.info("Vehicle movement saved successfully");
        return vehicleMovementRepository.save(vehicleMovement);
    }

    @Override
    public VehicleMovementDTO findVehicleMovementById(Long id) throws ResourceNotFoundException {
        Optional<VehicleMovement> vehicleMovementOptional =  vehicleMovementRepository.findById(id);
        if(vehicleMovementOptional.isPresent()){
            log.info("Vehicle movement found successfully");
            return vehicleMovementeToVehicleMovementDTO.convert(vehicleMovementOptional.get());
        }else{
            log.error("Failed to find vehicle movement by id: There is no movement with the provided id");
            throw new ResourceNotFoundException("There is no movement with the provided id");
        }
    }

    @Override
    public List<VehicleMovementDTO> findAllVehicleMovements() throws ResourceNotFoundException {
        List<VehicleMovementDTO> vehicleMovementDTOS = new ArrayList<>();
        List<VehicleMovement> vehicleMovementList = vehicleMovementRepository.findAll();
        if(vehicleMovementList.isEmpty()){
            log.error("Fail to list all vehicles movements: There are no vehicles movements");
            throw new ResourceNotFoundException("There are no vehicles movement");
        }
        for (VehicleMovement vehicleMovement : vehicleMovementList) {
            vehicleMovementDTOS.add(vehicleMovementeToVehicleMovementDTO.convert(vehicleMovement));
        }
        log.info("Founded all vehicles movements successfully");
        return vehicleMovementDTOS;
    }

    @Override
    public void deleteVehicleMovementById(Long id) throws ResourceNotFoundException {
        Optional<VehicleMovement> vehicleMovementOptional = vehicleMovementRepository.findById(id);
        vehicleMovementOptional.ifPresent(vehicleMovementRepository::delete);
        if (vehicleMovementOptional.isEmpty()){
            log.error("Failed to delete vehicle movement: The vehicle movement could not be found by the id provided");
            throw new ResourceNotFoundException("The vehicle movement doesn't exists");
        }

    }

    @Override
    public VehicleMovement updateVehicleMovement(VehicleMovement vehicleMovement) {
        return null;
    }

    @Override
    public List<VehicleMovementDTO> findVehicleMovementsByChasis(String chasis) throws ResourceNotFoundException {
        Vehicle vehicle = vehicleService.findVehicleById(chasis);
        if(vehicle == null){
            log.error("Failed to find vehicle movements by chasis: The vehicle with the chasis provided does not exits. ");
            throw new ResourceNotFoundException("The vehicle with the chasis provided does not exits.");
        }
        List<VehicleMovementDTO> vehicleMovementDTOList = new ArrayList<>();
        for (VehicleMovement vehicleMovement : vehicle.getVehicleMovementList()) {
            vehicleMovementDTOList.add(vehicleMovementeToVehicleMovementDTO.convert(vehicleMovement));
        }
        log.info("Vehicle movement founded successfully");
        return vehicleMovementDTOList;
    }


    private MovementType movementTypeHandler (Area area, boolean bool, String chasis) throws ResourceNotFoundException {
        MovementType movementType = null;
        Area areaToSet = null;
       switch (area){
           case LOGISTIC-> {
               if (bool){
                   areaToSet = Area.DELIVERED;
                   movementType = MovementType.LOGISTIC_CHECKOUT_TO_CLIENT;
               } else {
                   areaToSet = Area.TRANSITION;
                   movementType = MovementType.LOGISITIC_CHECKOUT;
               }
           }
           case PRODUCTION -> {
               areaToSet = Area.TRANSITION;
               movementType = MovementType.PRODUCTION_CHECKOUT;
               }
           case TRANSITION -> {
               if (bool){
                   areaToSet = Area.LOGISTIC;
                   movementType = MovementType.LOGISTIC_RECHECKIN;
               } else {
                   areaToSet = Area.PRODUCTION;
                   movementType = MovementType.PRODUCTION_CHECKIN;
               }
           }

       }
       vehicleService.updateVehicleAreaByMovementType(areaToSet, chasis);
       return movementType;
    }
}
