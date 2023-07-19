package com.example.aspblindajes.converters;

import com.example.aspblindajes.dto.WorkGroupDTO;
import com.example.aspblindajes.model.WorkGroup;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WorkGroupToWorkGroupDTO implements Converter<WorkGroup, WorkGroupDTO>{
    @Override
    public WorkGroupDTO convert(WorkGroup source) {
        WorkGroupDTO workGroupDTO = new WorkGroupDTO();
        workGroupDTO.setName(source.getName());

        return workGroupDTO;
    }
}
