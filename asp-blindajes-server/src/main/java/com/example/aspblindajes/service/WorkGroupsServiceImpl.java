package com.example.aspblindajes.service;

import com.example.aspblindajes.converters.WorkGroupDTOToWorkGroup;
import com.example.aspblindajes.dto.WorkGroupDTO;
import com.example.aspblindajes.exception.InvalidArgumentException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.WorkGroup;
import com.example.aspblindajes.repository.WorkGroupsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class WorkGroupsServiceImpl implements WorkGroupsService{

    private final WorkGroupsRepository workGroupsRepository;
    private final ConversionService conversionService;
    @Override
    public WorkGroup saveWorkGroups(WorkGroupDTO workGroupDTO) throws InvalidArgumentException {
        WorkGroup workGroupFounded =  workGroupsRepository.findWorkGroupsByName(workGroupDTO.getName());
        if(workGroupFounded != null){
            log.error("Fail to save work group: The work group already exists");
            throw new InvalidArgumentException("The work group already exists");
        }
        WorkGroup workGroup = conversionService.convert(workGroupDTO, WorkGroup.class);
        if(workGroup != null){
            log.info("Work group saved successfully");
           return workGroupsRepository.save(workGroup);
        }
        log.error("Fail to save work group: Invalid format for work groups");
        throw new InvalidArgumentException("Invalid format for work groups");

    }

    @Override
    public void deleteWorkGroupsById(Long id) throws ResourceNotFoundException {
        Optional<WorkGroup> optionalWorkGroups = workGroupsRepository.findById(id);
        if (optionalWorkGroups.isEmpty()){
            log.error("Fail to delete work group: There are no workGroups with the provided id");
            throw new ResourceNotFoundException("There are no workGroups with the provided id");
        }
        log.info("Work group deleted successfully");
        workGroupsRepository.deleteById(id);
    }

    @Override
    public WorkGroup updateWorkGroups(WorkGroup workGroup) throws ResourceNotFoundException {
        Optional<WorkGroup> optionalWorkGroups = workGroupsRepository.findById(workGroup.getId());
        if (optionalWorkGroups.isPresent()){
            log.info("Work Group updated successfully");
            workGroupsRepository.save(workGroup);
        }
        log.error("Fail to update Work Group: The workGroup you are trying to update doesn't exist");
        throw new ResourceNotFoundException("The workGroup you are trying to update doesn't exist");
    }

    @Override
    public List<WorkGroup> findAllWorkGroups() throws ResourceNotFoundException {
        List<WorkGroup> workGroupList = workGroupsRepository.findAll();
        if(workGroupList.size()>0){
            log.info("All work groups founded");
            return workGroupList;
        }
        log.error("Fail to list all workGroups:There are no workGroups on the database");
        throw new ResourceNotFoundException("There are no workGroups on the database");
    }

    @Override
    public WorkGroup findWorkGroupsByName(String name) throws ResourceNotFoundException {
        WorkGroup workGroup = workGroupsRepository.findWorkGroupsByName(name);
        if(workGroup == null){
            log.error("Fail to find the Work Group by name: The work group with the provided name doesn't exists ");
            throw new ResourceNotFoundException("The work group with the provided name doesn't exists");
        }

        log.info("Work Group found by name successfully");
        return workGroup;
    }
}
