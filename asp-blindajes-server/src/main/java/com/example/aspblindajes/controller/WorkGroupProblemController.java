package com.example.aspblindajes.controller;

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
    ResponseEntity<Double> getPercentage () throws ResourceNotFoundException {
        return ResponseEntity.ok(workGroupsService.getPercentageOfProblems());
    }

    @GetMapping("/percentageForWorkGroup")
    ResponseEntity<List<WorkGroupProblemQueryResponse>> getPercentageForWorkGroup () throws ResourceNotFoundException {
        return ResponseEntity.ok(workGroupsService.calculatePercentageOfProblemsForWorkGroup());
    }
    @GetMapping("/percentageInsideWorkGroup")
    ResponseEntity<WorkGroupProblemQueryResponse> getPercentageInsideWorkGroup (@RequestParam (value = "name") String name) throws ResourceNotFoundException {
        return ResponseEntity.ok(workGroupsService.calculatePercentageOfProblemsInsideWorkGroup(name));
    }
}
