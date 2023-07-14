package com.example.aspblindajes.service;

import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Vehicle;
import com.example.aspblindajes.repository.VehicleRepository;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService{

    private final VehicleRepository vehicleRepository;
    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
       if(vehicleRepository.findById(vehicle.getChasis()).isPresent()){
           throw new EntityExistsException("Ya existe un vehiculo con ese numero de chasis");
       }
       return vehicleRepository.save(vehicle);
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
        if(vehicleRepository.findById(id).isPresent()){
            vehicleRepository.deleteById(id);
        }
        throw new ResourceNotFoundException("No se encontro el vehiculo con ese numero de chasis");
    }

    @Override
    public List<Vehicle> findAllVehicles() throws ResourceNotFoundException {
        if (vehicleRepository.findAll().size() > 0){
            return vehicleRepository.findAll();
        }
        throw new ResourceNotFoundException("No se encontraron vehiculos");
    }

    @Override
    public Vehicle updateVehicle(Vehicle vehicle) throws ResourceNotFoundException {
        if(vehicleRepository.findById(vehicle.getChasis()).isPresent()){
            vehicleRepository.save(vehicle);
        }
        throw new ResourceNotFoundException("No se puede actualizar ya que no se encontro un vehiculo");
    }
}
