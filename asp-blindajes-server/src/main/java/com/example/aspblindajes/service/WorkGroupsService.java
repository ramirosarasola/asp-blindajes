package com.example.aspblindajes.service;

import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.WorkGroups;


import java.util.List;

public interface WorkGroupsService {
    WorkGroups saveWorkGroups(WorkGroups workGroups);
    void deleteWorkGroupsById(Long id) throws ResourceNotFoundException;
    WorkGroups updateWorkGroups(WorkGroups workGroups) throws ResourceNotFoundException;
    List<WorkGroups> listWorkGroups() throws ResourceNotFoundException;
    WorkGroups findWorkGroupsByName(String name) throws ResourceNotFoundException;
}
