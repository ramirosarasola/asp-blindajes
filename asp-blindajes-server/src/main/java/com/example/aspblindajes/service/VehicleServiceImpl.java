package com.example.aspblindajes.service;

import com.example.aspblindajes.converters.VehicleDTOToVehicleConverter;
import com.example.aspblindajes.dto.VehicleDTO;
import com.example.aspblindajes.exception.InvalidArgumentException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Area;
import com.example.aspblindajes.model.MovementType;
import com.example.aspblindajes.model.Vehicle;
import com.example.aspblindajes.model.VehicleMovement;
import com.example.aspblindajes.repository.VehicleMovementRepository;
import com.example.aspblindajes.repository.VehicleRepository;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class VehicleServiceImpl implements VehicleService{

    private final VehicleRepository vehicleRepository;
    private final VehicleDTOToVehicleConverter vehicleDTOToVehicleConverter;
    private final VehicleMovementRepository vehicleMovementRepository;
    @Override
    public Vehicle saveVehicle(VehicleDTO vehicleDTO) {
       Vehicle vehicle = vehicleDTOToVehicleConverter.convert(vehicleDTO);

       if(vehicleRepository.findById(vehicleDTO.getChasis()).isEmpty() && vehicle != null){

           if(Objects.equals(vehicleDTO.getBrandName(), "Ford") && vehicleDTO.getFordKey().isEmpty()){
               log.error("Fail to save vehicle: Ford's vehicles must have their unique key");
               throw new EntityExistsException("Ford's vehicles must have their unique key"); //todo -> change exception
           }
           VehicleMovement vehicleMovement = new VehicleMovement();
           vehicleMovement.setVehicle(vehicle);
           vehicleMovement.setMovementType(MovementType.LOGISTIC_CHECKIN);
           vehicleRepository.save(vehicle);
           vehicleMovementRepository.save(vehicleMovement);
           log.info("Vehicle saved successfully");
           return vehicle;
       }
       log.error("Fail to save vehicle: There is a vehicle with the chasis provided");
        throw new EntityExistsException("There is a vehicle with the chasis provided");
    }

    @Override
    public Vehicle findVehicleById(String id) throws ResourceNotFoundException {
        if(vehicleRepository.findById(id).isPresent()){
            log.info("Vehicle found by ID");
            return vehicleRepository.findById(id).get();
        }
        log.error("Fail to find vehicle by chasis: The vehicle could not be found by the provided chasis");
        throw new ResourceNotFoundException("The vehicle could not be found by the provided chasis");
    }

    @Override
    public void deleteVehicleById(String id) throws ResourceNotFoundException {
        if(vehicleRepository.findById(id).isEmpty()){
            log.error("Fail to delete vehicle: There is no vehicle with the provided ID (chasis) ");
            throw new ResourceNotFoundException("There is no vehicle with the provided ID (chasis)");
        }
        log.info("Vehicle deleted successfully ID: " + id);
        vehicleRepository.deleteById(id);
    }

    @Override
    public List<Vehicle> findAllVehicles() throws ResourceNotFoundException {
        if (vehicleRepository.findAll().size() > 0){
            log.info("All vehicles founded successfully");
            return vehicleRepository.findAll();
        }
        log.error("Fail to list all vehicles: There are no vehicle on the database");
        throw new ResourceNotFoundException("There are no vehicle on the database");
    }

    @Override
    public Vehicle updateVehicle(VehicleDTO vehicleDTO) throws ResourceNotFoundException {
        Vehicle vehicle = vehicleDTOToVehicleConverter.convert(vehicleDTO);

        if(vehicleRepository.findById(vehicleDTO.getChasis()).isPresent() && vehicle != null){
            log.info("Vehicle updated successfully");
            return vehicleRepository.save(vehicle);
        }
        log.error("Fail to update vehicle: The vehicle trying to update doesn't exists. ");
        throw new ResourceNotFoundException("The vehicle trying to update doesn't exists.");
    }

    @Override
    public Vehicle updateVehicleAreaByMovementType(Area area, String chasis) throws ResourceNotFoundException {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(chasis);
        if(optionalVehicle.isPresent()){
            Vehicle vehicle = optionalVehicle.get();
            vehicle.setArea(area);
            log.info("Vehicle area updated sucessfully");
            return vehicleRepository.save(vehicle);
        }
        log.error("Fail to update the vehicle area: The vehicle trying to update doesn't exists. ");
        throw new ResourceNotFoundException("The vehicle trying to update doesn't exists.");
    }
}
