package com.example.aspblindajes.converters;

import com.example.aspblindajes.dto.VehicleQualityControlDTO;
import com.example.aspblindajes.dto.WorkGroupProblemDTO;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Vehicle;
import com.example.aspblindajes.model.VehicleQualityControl;
import com.example.aspblindajes.model.WorkGroupProblem;
import com.example.aspblindajes.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class VehicleQualityControlDTOToVehicleQualityControl implements Converter <VehicleQualityControlDTO, VehicleQualityControl> {

    private final VehicleService vehicleService;
    @Override
    public VehicleQualityControl convert(VehicleQualityControlDTO source) {
        VehicleQualityControl vehicleQualityControl = new VehicleQualityControl();

        try {
            vehicleQualityControl.setVehicle(vehicleService.findVehicleById(source.getChasis()));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        vehicleQualityControl.setId(source.getId());
        vehicleQualityControl.setQualityControlDate(LocalDate.now());

        List<WorkGroupProblem> workGroupProblemList = new ArrayList<>();
        for (WorkGroupProblemDTO workGroupProblemDTO : source.getWorkGroupProblemDTOList()) {
            WorkGroupProblem workGroupProblem = new WorkGroupProblem();
            workGroupProblem.setHasProblem(workGroupProblemDTO.getHasProblem());
            workGroupProblem.setProblemDescription(workGroupProblemDTO.getProblemDescription());

            workGroupProblemList.add(workGroupProblem);
        }

        vehicleQualityControl.setWorkGroupProblemList(workGroupProblemList);

        return vehicleQualityControl;
    }
}
