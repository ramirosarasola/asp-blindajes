package com.example.aspblindajes.service;

import com.example.aspblindajes.converters.WorkGroupDTOToWorkGroup;
import com.example.aspblindajes.dto.WorkGroupDTO;
import com.example.aspblindajes.exception.InvalidArgumentException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.WorkGroup;
import com.example.aspblindajes.repository.WorkGroupsRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WorkGroupsServiceImpl implements WorkGroupsService{

    private final WorkGroupsRepository workGroupsRepository;
    private final ConversionService conversionService;
    @Override
    public WorkGroup saveWorkGroups(WorkGroupDTO workGroupDTO) throws InvalidArgumentException {
        WorkGroup workGroup = conversionService.convert(workGroupDTO, WorkGroup.class);
        if(workGroup != null){
           return workGroupsRepository.save(workGroup);
        }
        throw new InvalidArgumentException("a");

    }

    @Override
    public void deleteWorkGroupsById(Long id) throws ResourceNotFoundException {
        Optional<WorkGroup> optionalWorkGroups = workGroupsRepository.findById(id);
        if (optionalWorkGroups.isEmpty()){
            throw new ResourceNotFoundException("There are no workGroups with the provided id");
        }
        workGroupsRepository.deleteById(id);
    }

    @Override
    public WorkGroup updateWorkGroups(WorkGroup workGroup) throws ResourceNotFoundException {
        Optional<WorkGroup> optionalWorkGroups = workGroupsRepository.findById(workGroup.getId());
        if (optionalWorkGroups.isPresent()){
            workGroupsRepository.save(workGroup);
        }
        throw new ResourceNotFoundException("The workGroup you are trying to update doesn't exist");
    }

    @Override
    public List<WorkGroup> findAllWorkGroups() throws ResourceNotFoundException {
        List<WorkGroup> workGroupList = workGroupsRepository.findAll();
        if(workGroupList.size()>0){
            return workGroupList;
        }
        throw new ResourceNotFoundException("There are no existant workGroups");
    }

    @Override
    public WorkGroup findWorkGroupsByName(String name) throws ResourceNotFoundException {
        return null;
    }
}
