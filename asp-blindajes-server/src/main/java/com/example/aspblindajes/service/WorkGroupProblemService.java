package com.example.aspblindajes.service;

import com.example.aspblindajes.dto.ProblemForModelResponse;
import com.example.aspblindajes.dto.ResponseWGPFilter;
import com.example.aspblindajes.dto.TotalPercentageQueryResponse;
import com.example.aspblindajes.dto.WorkGroupProblemQueryResponse;
import com.example.aspblindajes.exception.InvalidArgumentException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.WorkGroup;
import com.example.aspblindajes.model.WorkGroupProblem;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkGroupProblemService {

    WorkGroupProblem getWorkGroupProblemById (Long id) throws ResourceNotFoundException;

    List<WorkGroupProblem> findAllWorkGroupProblems () throws ResourceNotFoundException;

    WorkGroupProblem updateWorkGroupProblem (WorkGroupProblem workGroupProblem) throws InvalidArgumentException;

    TotalPercentageQueryResponse getPercentageOfProblems () throws ResourceNotFoundException;

    List<WorkGroupProblemQueryResponse> calculatePercentageOfProblemsForWorkGroup (int mes) throws ResourceNotFoundException;

//    List<WorkGroupProblemQueryResponse> calculatePercentageOfProblemsInsideWorkGroup () throws ResourceNotFoundException;

    Long countWorkGroupProblemsWithProblem (int mes) throws ResourceNotFoundException;

    List<ProblemForModelResponse> getProblemForModel (int mes);

    List<ResponseWGPFilter> getWorkGroupProblemsByFilter(String chasis, String workGroup,String startDate, String endDate);
}
