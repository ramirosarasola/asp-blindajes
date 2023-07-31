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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService{

    private final VehicleRepository vehicleRepository;
    private final VehicleDTOToVehicleConverter vehicleDTOToVehicleConverter;
    private final VehicleMovementRepository vehicleMovementRepository;
    @Override
    public Vehicle saveVehicle(VehicleDTO vehicleDTO) {
       Vehicle vehicle = vehicleDTOToVehicleConverter.convert(vehicleDTO);

       if(vehicleRepository.findById(vehicleDTO.getChasis()).isEmpty() && vehicle != null){

           if(Objects.equals(vehicleDTO.getBrandName(), "Ford") && vehicleDTO.getFordKey().isEmpty()){
               throw new EntityExistsException("Ford's vehicles must have their unique key"); //todo -> change exception
           }
           VehicleMovement vehicleMovement = new VehicleMovement();
           vehicleMovement.setVehicle(vehicle);
           vehicleMovement.setMovementType(MovementType.LOGISTIC_CHECKIN);
           vehicleRepository.save(vehicle);
           vehicleMovementRepository.save(vehicleMovement);
            return vehicle;
       }
        throw new EntityExistsException("Ya existe un vehiculo con ese numero de chasis");
    }

    @Override
    public Vehicle findVehicleById(String id) throws ResourceNotFoundException {
        if(vehicleRepository.findById(id).isPresent()){
            return vehicleRepository.findById(id).get();
        }
        throw new ResourceNotFoundException("No se encontro el vehiculo con ese numero de chasis");
    }

    @Override
    public void deleteVehicleById(String id) throws ResourceNotFoundException {
        if(vehicleRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("There is no vehicle with the provided ID (chasis)");
        }
        vehicleRepository.deleteById(id);
    }

    @Override
    public List<Vehicle> findAllVehicles() throws ResourceNotFoundException {
        if (vehicleRepository.findAll().size() > 0){
            return vehicleRepository.findAll();
        }
        throw new ResourceNotFoundException("No se encontraron vehiculos");
    }

    @Override
    public Vehicle updateVehicle(VehicleDTO vehicleDTO) throws ResourceNotFoundException {
        Vehicle vehicle = vehicleDTOToVehicleConverter.convert(vehicleDTO);

        if(vehicleRepository.findById(vehicleDTO.getChasis()).isPresent() && vehicle != null){
            return vehicleRepository.save(vehicle);
        }
        throw new ResourceNotFoundException("The vehicle trying to update doesn't exists.");
    }

    @Override
    public Vehicle updateVehicleAreaByMovementType(Area area, String chasis) throws ResourceNotFoundException {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(chasis);
        if(optionalVehicle.isPresent()){
            Vehicle vehicle = optionalVehicle.get();
            vehicle.setArea(area);
            return vehicleRepository.save(vehicle);
        }
        throw new ResourceNotFoundException("The vehicle trying to update doesn't exists.");
    }
}
