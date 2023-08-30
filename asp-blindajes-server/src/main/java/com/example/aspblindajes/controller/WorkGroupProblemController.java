package com.example.aspblindajes.controller;

import com.example.aspblindajes.dto.ProblemForModelResponse;
import com.example.aspblindajes.dto.ResponseWGPFilter;
import com.example.aspblindajes.dto.TotalPercentageQueryResponse;
import com.example.aspblindajes.dto.WorkGroupProblemQueryResponse;
import com.example.aspblindajes.exception.InvalidArgumentException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.WorkGroupProblem;
import com.example.aspblindajes.service.WorkGroupProblemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/workGroupsProblem")
public class WorkGroupProblemController {
    private final WorkGroupProblemService workGroupsService;



    @GetMapping("/all")
    public ResponseEntity<List<WorkGroupProblem>> findAllWorkGroupsProblems () throws ResourceNotFoundException {
        return ResponseEntity.ok(workGroupsService.findAllWorkGroupProblems());
    }

    @GetMapping
    public ResponseEntity<WorkGroupProblem> findWorkGroupsProblemById (@RequestParam(value = "id") Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(workGroupsService.getWorkGroupProblemById(id));
    }

    @PutMapping
    public ResponseEntity<WorkGroupProblem> updateWorkGroups (@RequestBody WorkGroupProblem workGroupProblem) throws InvalidArgumentException {
        return ResponseEntity.ok(workGroupsService.updateWorkGroupProblem(workGroupProblem));
    }

    @GetMapping("/totalPercentage")
    ResponseEntity<TotalPercentageQueryResponse> getPercentage () throws ResourceNotFoundException {
        return ResponseEntity.ok(workGroupsService.getPercentageOfProblems());
    }

    @GetMapping("/percentageForWorkGroup")
    ResponseEntity<List<WorkGroupProblemQueryResponse>> getPercentageForWorkGroup (@RequestParam("mes") int mes) throws ResourceNotFoundException {
        return ResponseEntity.ok(workGroupsService.calculatePercentageOfProblemsForWorkGroup(mes));
    }
//    @GetMapping("/percentageInsideWorkGroup")
//    ResponseEntity<List<WorkGroupProblemQueryResponse>> getPercentageInsideWorkGroup () throws ResourceNotFoundException {
//        return ResponseEntity.ok(workGroupsService.calculatePercentageOfProblemsInsideWorkGroup());
//    }

    @GetMapping("/countWorkGroupWithProblem")
    ResponseEntity<Long> countWorkGroupWithProblem () throws ResourceNotFoundException{
        return ResponseEntity.ok(workGroupsService.countWorkGroupProblemsWithProblem());
    }

    @GetMapping("/countProblemsForModel")
    ResponseEntity<List<ProblemForModelResponse>> countProblemsForModel () {
        return ResponseEntity.ok(workGroupsService.getProblemForModel());
    }

    @GetMapping("/filters")
    ResponseEntity<List<ResponseWGPFilter>> getWGPByFilters (@RequestParam (value = "chasis", required = false) String chasis,
                                                             @RequestParam (value = "workGroupName", required = false) String workGroupName,
                                                             @RequestParam (value = "startDate", required = false) String startDate,
                                                             @RequestParam (value = "endDate", required = false) String endDate){
        return ResponseEntity.ok(workGroupsService.getWorkGroupProblemsByFilter(chasis, workGroupName, startDate, endDate));
    }
}
