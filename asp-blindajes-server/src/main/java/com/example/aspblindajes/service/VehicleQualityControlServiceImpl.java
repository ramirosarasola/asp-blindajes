package com.example.aspblindajes.service;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.VehicleQualityControl;
import com.example.aspblindajes.repository.VehicleQualityControlRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VehicleQualityControlServiceImpl implements VehicleQualityControlService{

    private final VehicleQualityControlRepository vehicleQualityControlRepository;
    private final WorkGroupsService workGroupsService;

    @Override
    public VehicleQualityControl saveVehicleQualityControl(VehicleQualityControl vehicleQualityControl) {
        return vehicleQualityControlRepository.save(vehicleQualityControl);
    }

    @Override
    public void deleteVehicleQualityControlById(Long id) throws ResourceNotFoundException {
        Optional<VehicleQualityControl> vehicleQualityControlOptional = vehicleQualityControlRepository.findById(id);
        if(vehicleQualityControlOptional.isEmpty()){
            throw new ResourceNotFoundException("There are no quality controls with the given id");
        }
        vehicleQualityControlRepository.deleteById(id);
    }

    @Override
    public VehicleQualityControl findVehicleQualityControlById(Long id) throws ResourceNotFoundException{
        Optional<VehicleQualityControl> vehicleQualityControlOptional = vehicleQualityControlRepository.findById(id);
        if(vehicleQualityControlOptional.isEmpty()){
            throw new ResourceNotFoundException("The required vehicle control quality does not exists");
        }
        return vehicleQualityControlOptional.get();
    }

    @Override
    public List<VehicleQualityControl> findAllVehicleQualiControl() throws ResourceNotFoundException {
        List<VehicleQualityControl> vehicleQualityControlList = vehicleQualityControlRepository.findAll();
        if (vehicleQualityControlList.size() > 0){
            return vehicleQualityControlList;
        }
        throw new ResourceNotFoundException("There are no existing quality controls");
    }

    @Override
    public VehicleQualityControl updateVehicleQualityControl(VehicleQualityControl vehicleQualityControl) throws ResourceNotFoundException{
        Optional<VehicleQualityControl> vehicleQualityControlOptional = vehicleQualityControlRepository.findById(vehicleQualityControl.getId());
        if(vehicleQualityControlOptional.isEmpty()){
            throw new ResourceNotFoundException("The quality control you are trying to update does not exits");
        }
        return vehicleQualityControlRepository.save(vehicleQualityControl);
    }
}
