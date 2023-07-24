package com.example.aspblindajes.converters;

import com.example.aspblindajes.dto.WorkGroupProblemDTO;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.WorkGroupProblem;
import com.example.aspblindajes.service.VehicleQualityControlService;
import com.example.aspblindajes.service.WorkGroupsService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WorkGroupProblemDTOToWorkGroupProblem implements Converter<WorkGroupProblemDTO, WorkGroupProblem> {

    private final WorkGroupsService workGroupsService;

    @Override
    public WorkGroupProblem convert(WorkGroupProblemDTO source) {
        WorkGroupProblem workGroupProblem = new WorkGroupProblem();
        workGroupProblem.setHasProblem(source.getHasProblem());
        workGroupProblem.setProblemDescription(source.getProblemDescription());
        workGroupProblem.setId(source.getId());

        try {
            workGroupProblem.setWorkGroup(workGroupsService.findWorkGroupsByName(source.getWorkGroupName()));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        return workGroupProblem;
    }
}
