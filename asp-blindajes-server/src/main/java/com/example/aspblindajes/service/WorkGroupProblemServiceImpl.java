package com.example.aspblindajes.service;

import com.example.aspblindajes.dto.WorkGroupProblemQueryResponse;
import com.example.aspblindajes.exception.InvalidArgumentException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.WorkGroupProblem;
import com.example.aspblindajes.repository.WorkGroupProblemRepository;
import com.example.aspblindajes.repository.WorkGroupsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class WorkGroupProblemServiceImpl implements WorkGroupProblemService{

    private final WorkGroupProblemRepository workGroupProblemRepository;

    @Override
    public WorkGroupProblem getWorkGroupProblemById(Long id) throws ResourceNotFoundException {
       Optional<WorkGroupProblem> workGroupProblem = workGroupProblemRepository.findById(id);
       if (workGroupProblem.isPresent()){
           return workGroupProblem.get();
        }
       throw new ResourceNotFoundException("There are no workGroupProblems with the provided id");
    }

    @Override
    public List<WorkGroupProblem> findAllWorkGroupProblems() throws ResourceNotFoundException {
        List<WorkGroupProblem> workGroupProblemList = workGroupProblemRepository.findAll();
        if (workGroupProblemList.size() > 0){
            return workGroupProblemList;
        }
        throw new ResourceNotFoundException("There are no workGroupsProblems in the database");
    }

    @Override
    public WorkGroupProblem updateWorkGroupProblem(WorkGroupProblem workGroupProblem) throws InvalidArgumentException {
        Optional<WorkGroupProblem> workGroupProblem1 = workGroupProblemRepository.findById(workGroupProblem.getId());
        if (workGroupProblem1.isPresent()){
            return workGroupProblemRepository.save(workGroupProblem);
        }
        throw new InvalidArgumentException("The provided id does not match with any workGroup registered in the dataBase");
    }

    @Override
    public double getPercentageOfProblems() throws ResourceNotFoundException {
        if(workGroupProblemRepository.findAll().size() > 0){
            return workGroupProblemRepository.totalQtyOfProblems();
        }
        throw new ResourceNotFoundException("there are no workGroupProblems to get the percentage of");
    }

    @Override
    public List<WorkGroupProblemQueryResponse> calculatePercentageOfProblemsForWorkGroup() throws ResourceNotFoundException {
        List<WorkGroupProblem> workGroupProblemList = workGroupProblemRepository.findAll();
        List<WorkGroupProblemQueryResponse> workGroupProblemQueryResponseList = new ArrayList<>();
        if(workGroupProblemList.size() > 0){
            for (WorkGroupProblem workGroupProblem : workGroupProblemList) {
                WorkGroupProblemQueryResponse workGroupProblemQueryResponse = new WorkGroupProblemQueryResponse();
                workGroupProblemQueryResponse.setName(workGroupProblem.getWorkGroup().getName());
                workGroupProblemQueryResponse.setPercentageValue(workGroupProblemRepository.calculatePercentageOfProblemsForWorkGroup(workGroupProblem.getWorkGroup().getName()));
                workGroupProblemQueryResponseList.add(workGroupProblemQueryResponse);
            }

            return workGroupProblemQueryResponseList;
        }
        throw new ResourceNotFoundException("there are no workGroupProblems to get the percentage of");
    }

    @Override
    public WorkGroupProblemQueryResponse calculatePercentageOfProblemsInsideWorkGroup(String name) throws ResourceNotFoundException {
        if (workGroupProblemRepository.findAll().size() > 0) {
            WorkGroupProblemQueryResponse workGroupProblemQueryResponse = new WorkGroupProblemQueryResponse();
            workGroupProblemQueryResponse.setName(name);
            workGroupProblemQueryResponse.setPercentageValue(workGroupProblemRepository.calculatePercentageOfProblemsInsideWorkGroup(name));
            return workGroupProblemQueryResponse;

        }
        throw new ResourceNotFoundException("there are no workGroupProblems to get the percentage of");
    }
}
