package com.example.aspblindajes.controller;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.WorkGroups;
import com.example.aspblindajes.service.WorkGroupsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/WorkGroups")
public class WorkGroupsController {

     private final WorkGroupsService workGroupsService;

     @PostMapping
    public ResponseEntity<WorkGroups> saveWorkGroup (@RequestBody  WorkGroups workGroups) {
         return ResponseEntity.ok(workGroupsService.saveWorkGroups(workGroups));
     }

     @GetMapping("/all")
    public ResponseEntity<List<WorkGroups>> findAllWorkGroups () throws ResourceNotFoundException {
         return ResponseEntity.ok(workGroupsService.findAllWorkGroups());
     }

     @GetMapping
    public ResponseEntity<WorkGroups> findWorkGroupsByName (@RequestParam (value = "name") String name) throws ResourceNotFoundException {
         return ResponseEntity.ok(workGroupsService.findWorkGroupsByName(name));
     }


    @DeleteMapping
    public ResponseEntity<String> deleteWorkGroupById(@RequestParam(value = "id") Long id) throws ResourceNotFoundException{
         workGroupsService.deleteWorkGroupsById(id);
         return ResponseEntity.ok("Work Group deleted successfully");
    }

    @PutMapping
    public ResponseEntity<WorkGroups> updateWorkGroups (@RequestBody WorkGroups workGroups) throws ResourceNotFoundException {
         return ResponseEntity.ok(workGroupsService.updateWorkGroups(workGroups));
    }


}
