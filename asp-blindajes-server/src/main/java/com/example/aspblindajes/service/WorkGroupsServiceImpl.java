package com.example.aspblindajes.service;

import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.WorkGroups;
import com.example.aspblindajes.repository.WorkGroupsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WorkGroupsServiceImpl implements WorkGroupsService{

    private final WorkGroupsRepository workGroupsRepository;
    @Override
    public WorkGroups saveWorkGroups(WorkGroups workGroups, String workGroupType) {


        return null;
    }

    @Override
    public void deleteWorkGroupsById(Long id) throws ResourceNotFoundException {
        Optional<WorkGroups> optionalWorkGroups = workGroupsRepository.findById(id);
        if (optionalWorkGroups.isPresent()){
            workGroupsRepository.deleteById(id);
        }
        throw new ResourceNotFoundException("There are no workGroups with the provided id");

    }

    @Override
    public WorkGroups updateWorkGroups(WorkGroups workGroups) throws ResourceNotFoundException {
        Optional<WorkGroups> optionalWorkGroups = workGroupsRepository.findById(workGroups.getId());
        if (optionalWorkGroups.isPresent()){
            workGroupsRepository.save(workGroups);
        }
        throw new ResourceNotFoundException("The workGroup you are trying to update doesn't exist");
    }

    @Override
    public List<WorkGroups> findAllWorkGroups() throws ResourceNotFoundException {
        List<WorkGroups> workGroupsList = workGroupsRepository.findAll();
        if(workGroupsList.size()>0){
            return workGroupsList;
        }
        throw new ResourceNotFoundException("There are no existant workGroups");
    }

    @Override
    public WorkGroups findWorkGroupsByName(String name) throws ResourceNotFoundException {
        return null;
    }
}
