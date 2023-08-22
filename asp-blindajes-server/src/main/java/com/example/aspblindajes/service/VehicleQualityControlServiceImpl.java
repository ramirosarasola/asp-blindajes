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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
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

        log.info("Vehicle QC saved successfully");
        return vehicleQualityControlRepository.save(vehicleQualityControl);
    }

    @Override
    public void deleteVehicleQualityControlById(Long id) throws ResourceNotFoundException {
        Optional<VehicleQualityControl> vehicleQualityControlOptional = vehicleQualityControlRepository.findById(id);
        if(vehicleQualityControlOptional.isEmpty()){
            log.error("Fail to delete vehicle QC by ID: There are no quality controls with the given id ");
            throw new ResourceNotFoundException("There are no quality controls with the given id");
        }
        log.info("Vehicle QC deleted successfully");
        vehicleQualityControlRepository.deleteById(id);
    }

    @Override
    public VehicleQualityControl findVehicleQualityControlById(Long id) throws ResourceNotFoundException{
        Optional<VehicleQualityControl> vehicleQualityControlOptional = vehicleQualityControlRepository.findById(id);
        if(vehicleQualityControlOptional.isEmpty()){
            log.error("Fail to find vehicle QC by ID: The provided vehicle control quality does not exists");
            throw new ResourceNotFoundException("The provided vehicle control quality does not exists");
        }
        log.info("Vehicle QC found successfully");
        return vehicleQualityControlOptional.get();
    }

    @Override
    public List<VehicleQualityControl> findAllVehicleQualityControl() throws ResourceNotFoundException {
        List<VehicleQualityControl> vehicleQualityControlList = vehicleQualityControlRepository.findAll();
        if (vehicleQualityControlList.size() > 0){
            log.info("All vehicles QC founded successfully");
            return vehicleQualityControlList;
        }
        log.error("Fail to list vehicles QC: There are no existing quality controls");
        throw new ResourceNotFoundException("There are no existing quality controls");
    }

    @Override
    public VehicleQualityControl updateVehicleQualityControl(VehicleQualityControlDTO vehicleQualityControlDTO) throws ResourceNotFoundException{
        Optional<VehicleQualityControl> vehicleQualityControlOptional = vehicleQualityControlRepository.findById(vehicleQualityControlDTO.getId());
        if(vehicleQualityControlOptional.isEmpty()){
            log.error("Fail to update the vehicle QC: The quality control you are trying to update does not exits");
            throw new ResourceNotFoundException("The quality control you are trying to update does not exits");
        }

        VehicleQualityControl vehicleQualityControl = vehicleQualityControlDTOToVehicleQualityControl.convert(vehicleQualityControlDTO);
        log.info("Vehicle QC updated successfully");
        return vehicleQualityControlRepository.save(vehicleQualityControl);

    }

    public List<VehicleQualityControl> findVehicleQualityControlByVehicleId (String id) throws ResourceNotFoundException {
        List<VehicleQualityControl> vehicleQualityControlList = vehicleQualityControlRepository.findVehicleQualityControlByVehicleId(id);
        if (vehicleQualityControlList.size() > 0){
            return vehicleQualityControlList;
        }
        throw new ResourceNotFoundException("There are no quality control assigned to this chasis");
    }
}
