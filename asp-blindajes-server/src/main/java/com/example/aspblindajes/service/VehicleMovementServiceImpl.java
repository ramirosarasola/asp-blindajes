package com.example.aspblindajes.service;

import com.example.aspblindajes.converters.VehicleMovementeToVehicleMovementDTO;
import com.example.aspblindajes.dto.VehicleDTO;
import com.example.aspblindajes.dto.VehicleMovementDTO;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.*;
import com.example.aspblindajes.repository.UserRepository;
import com.example.aspblindajes.repository.VehicleMovementRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private final UserRepository userRepository;


    @Override
    public VehicleMovement saveVehicleMovement(String chasis, String userName) throws Exception {
        Vehicle vehicle = vehicleService.findVehicleByChasis(chasis);
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
        vehicleMovement.setUser(userRepository.findUserByUsername(userName).get());
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

        if (vehicleMovementOptional.isPresent() && !vehicleMovementOptional.get().getMovementType().equals(MovementType.LOGISTIC_CHECKIN)){
            vehicleMovementRepository.deleteById(id);
            movementTypeDeleteHandler(vehicleMovementOptional.get().getMovementType(), vehicleMovementOptional.get().getVehicle().getChasis());
            log.info("VehicleMovement Deleted, Vehicle area updated succesfully");

        }
        if (vehicleMovementOptional.isEmpty()){
            log.error("Failed to delete vehicle movement: The vehicle movement could not be found by the id provided or you are trying to delete a checkin movement");
            throw new ResourceNotFoundException("The vehicle movement doesn't exists or you are trying to delete a checkin movement");
        }

    }

    @Override
    public VehicleMovement updateVehicleMovement(VehicleMovement vehicleMovement) {
    return null;

    }

    @Override
    public List<VehicleMovementDTO> findVehicleMovementsByChasis(String chasis) throws ResourceNotFoundException {
        Vehicle vehicle = vehicleService.findVehicleByChasis(chasis);
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

    @Override
    public List<VehicleMovementDTO> getMovementsByFilter(String mtName, String vehicleId, String startDate, Long userId,  String endDate) {
        List<VehicleMovementDTO> movementDTOS = new ArrayList<>();
        if (mtName == null && vehicleId == null && startDate == null && endDate == null) {
            List<VehicleMovement> movementList = vehicleMovementRepository.findAll();
            for (VehicleMovement movement : movementList) {
                movementDTOS.add(vehicleMovementeToVehicleMovementDTO.convert(movement));
            }
        }else {
            List<VehicleMovement> movementList = vehicleMovementRepository.getMovementsByFilter(mtName, vehicleId, dateStartConverter(startDate),userId, dateEndConverter(endDate));
            for (VehicleMovement movement : movementList) {
                movementDTOS.add(vehicleMovementeToVehicleMovementDTO.convert(movement));
            }
        }
        return movementDTOS;
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
                   areaToSet = Area.PRODUCTION;
                   movementType = MovementType.LOGISITIC_CHECKOUT_TO_PRODUCTION;
               }
           }
           case PRODUCTION -> {
               areaToSet = Area.LOGISTIC;
               movementType = MovementType.PRODUCTION_CHECKOUT_TO_LOGISTIC;
               }

       }
       vehicleService.updateVehicleAreaByMovementType(areaToSet, chasis);
       return movementType;
    }

    private void movementTypeDeleteHandler (MovementType mt, String chasis) throws ResourceNotFoundException{
        Area area = null;
        switch (mt) {
            case LOGISITIC_CHECKOUT_TO_PRODUCTION, LOGISTIC_CHECKOUT_TO_CLIENT -> {
                area = Area.LOGISTIC;
            }
            case PRODUCTION_CHECKOUT_TO_LOGISTIC -> {
                area = Area.PRODUCTION;
            }

        }
            vehicleService.updateVehicleAreaByMovementType(area, chasis);
    }



    private LocalDateTime dateStartConverter(String date) {
        if (date != null && !date.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, formatter).atStartOfDay();
        } else {
            return null;
        }
    }

    private LocalDateTime dateEndConverter(String date) {
        if (date != null && !date.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, formatter).atTime(23, 59);
        } else {
            return null;
        }
    }
}
