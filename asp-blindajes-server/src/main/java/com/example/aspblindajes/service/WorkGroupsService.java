package com.example.aspblindajes.service;

import com.example.aspblindajes.dto.WorkGroupDTO;
import com.example.aspblindajes.exception.InvalidArgumentException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Brand;
import com.example.aspblindajes.model.Client;
import com.example.aspblindajes.model.WorkGroup;


import java.util.List;

public interface WorkGroupsService {
    WorkGroup saveWorkGroups(WorkGroupDTO workGroupDTO) throws InvalidArgumentException;
    void deleteWorkGroupsById(Long id) throws ResourceNotFoundException;
    WorkGroup updateWorkGroups(WorkGroup workGroup) throws ResourceNotFoundException;
    List<WorkGroup> findAllWorkGroups() throws ResourceNotFoundException;
    WorkGroup findWorkGroupsByName(String name) throws ResourceNotFoundException;

    WorkGroup wgSetHidden (Long id) throws ResourceNotFoundException;
    List<WorkGroup> listWGHiddenFalse () throws ResourceNotFoundException;
}
