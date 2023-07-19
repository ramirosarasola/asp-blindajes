package com.example.aspblindajes.converters;

import com.example.aspblindajes.dto.WorkGroupDTO;
import com.example.aspblindajes.model.WorkGroup;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WorkGroupDTOToWorkGroup implements Converter<WorkGroupDTO, WorkGroup> {
    @Override
    public WorkGroup convert(WorkGroupDTO source) {
        WorkGroup workGroup = new WorkGroup();
        workGroup.setName(source.getName());

        return workGroup;
    }
}
