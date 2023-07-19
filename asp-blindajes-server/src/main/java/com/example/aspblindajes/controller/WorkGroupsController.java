package com.example.aspblindajes.controller;
import com.example.aspblindajes.dto.WorkGroupDTO;
import com.example.aspblindajes.exception.InvalidArgumentException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.WorkGroup;
import com.example.aspblindajes.service.WorkGroupsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/workGroups")
public class WorkGroupsController {

     private final WorkGroupsService workGroupsService;

     @PostMapping
    public ResponseEntity<WorkGroup> saveWorkGroup (@RequestBody WorkGroupDTO workGroupDTO) throws InvalidArgumentException {
         return ResponseEntity.ok(workGroupsService.saveWorkGroups(workGroupDTO));
     }

     @GetMapping("/all")
    public ResponseEntity<List<WorkGroup>> findAllWorkGroups () throws ResourceNotFoundException {
         return ResponseEntity.ok(workGroupsService.findAllWorkGroups());
     }

     @GetMapping
    public ResponseEntity<WorkGroup> findWorkGroupsByName (@RequestParam (value = "name") String name) throws ResourceNotFoundException {
         return ResponseEntity.ok(workGroupsService.findWorkGroupsByName(name));
     }


    @DeleteMapping
    public ResponseEntity<String> deleteWorkGroupById(@RequestParam(value = "id") Long id) throws ResourceNotFoundException{
         workGroupsService.deleteWorkGroupsById(id);
         return ResponseEntity.ok("Work Group deleted successfully");
    }

    @PutMapping
    public ResponseEntity<WorkGroup> updateWorkGroups (@RequestBody WorkGroup workGroup) throws ResourceNotFoundException {
         return ResponseEntity.ok(workGroupsService.updateWorkGroups(workGroup));
    }


}
