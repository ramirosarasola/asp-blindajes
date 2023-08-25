package com.example.aspblindajes.service;

import com.example.aspblindajes.dto.WorkGroupProblemQueryResponse;
import com.example.aspblindajes.exception.InvalidArgumentException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.WorkGroup;
import com.example.aspblindajes.model.WorkGroupProblem;

import java.util.List;

public interface WorkGroupProblemService {

    WorkGroupProblem getWorkGroupProblemById (Long id) throws ResourceNotFoundException;

    List<WorkGroupProblem> findAllWorkGroupProblems () throws ResourceNotFoundException;

    WorkGroupProblem updateWorkGroupProblem (WorkGroupProblem workGroupProblem) throws InvalidArgumentException;

    double getPercentageOfProblems () throws ResourceNotFoundException;

    List<WorkGroupProblemQueryResponse> calculatePercentageOfProblemsForWorkGroup () throws ResourceNotFoundException;

    List<WorkGroupProblemQueryResponse> calculatePercentageOfProblemsInsideWorkGroup () throws ResourceNotFoundException;

    Long countWorkGroupProblemsWithProblem () throws ResourceNotFoundException;
}
