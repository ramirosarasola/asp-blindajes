package com.example.aspblindajes.service;
import com.example.aspblindajes.converters.VehicleQualityControlDTOToVehicleQualityControl;
import com.example.aspblindajes.converters.WorkGroupProblemDTOToWorkGroupProblem;
import com.example.aspblindajes.dto.VehicleQualityControlDTO;
import com.example.aspblindajes.dto.WorkGroupProblemDTO;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.VehicleQualityControl;
import com.example.aspblindajes.model.WorkGroupProblem;
import com.example.aspblindajes.repository.VehicleQualityControlRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VehicleQualityControlServiceImpl implements VehicleQualityControlService{

    private final VehicleQualityControlRepository vehicleQualityControlRepository;
    private final VehicleQualityControlDTOToVehicleQualityControl vehicleQualityControlDTOToVehicleQualityControl;
    @Override
    public VehicleQualityControl saveVehicleQualityControl(VehicleQualityControlDTO vehicleQualityControlDTO) {
        boolean canBeCheckedOut = true;
        for(WorkGroupProblemDTO workGroupProblemDTO: vehicleQualityControlDTO.getWorkGroupProblemDTOList()){
            if (workGroupProblemDTO.getHasProblem()) {
                canBeCheckedOut = false;
                break;
            }
        }

        VehicleQualityControl vehicleQualityControl = vehicleQualityControlDTOToVehicleQualityControl.convert(vehicleQualityControlDTO);
        vehicleQualityControl.setCanBeCheckedOut(canBeCheckedOut);

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
    public List<VehicleQualityControl> findAllVehicleQualityControl() throws ResourceNotFoundException {
        List<VehicleQualityControl> vehicleQualityControlList = vehicleQualityControlRepository.findAll();
        if (vehicleQualityControlList.size() > 0){
            return vehicleQualityControlList;
        }
        throw new ResourceNotFoundException("There are no existing quality controls");
    }

    @Override
    public VehicleQualityControl updateVehicleQualityControl(VehicleQualityControlDTO vehicleQualityControlDTO) throws ResourceNotFoundException{
        Optional<VehicleQualityControl> vehicleQualityControlOptional = vehicleQualityControlRepository.findById(vehicleQualityControlDTO.getId());
        if(vehicleQualityControlOptional.isEmpty()){
            throw new ResourceNotFoundException("The quality control you are trying to update does not exits");
        }

        VehicleQualityControl vehicleQualityControl = vehicleQualityControlDTOToVehicleQualityControl.convert(vehicleQualityControlDTO);
        return vehicleQualityControlRepository.save(vehicleQualityControl);

    }
}
