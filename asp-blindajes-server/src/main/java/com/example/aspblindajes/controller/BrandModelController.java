package com.example.aspblindajes.controller;

import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.BrandModel;
import com.example.aspblindajes.service.BrandModelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/model")
public class BrandModelController {

    private final BrandModelService brandModelService;
    @PostMapping
    public ResponseEntity<Map<String, String>> saveBrandModel(@RequestBody BrandModel brandModel) throws ResourceAlreadyExistsException {
        brandModelService.saveBrandModel(brandModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("message","Model created successfully"));
    }

    @GetMapping()
    public ResponseEntity<BrandModel> getBrandModelByName(@RequestParam(value = "name") String name) throws ResourceNotFoundException {
        return ResponseEntity.ok(brandModelService.findBrandModelByName(name));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BrandModel>> listBrandModels() throws ResourceNotFoundException {
        return ResponseEntity.ok(brandModelService.listBrandModels());
    }

    @PutMapping()
    public ResponseEntity<Map<String, String>> updateBrandModel(@RequestBody BrandModel brandModel) throws ResourceNotFoundException {
        brandModelService.updateBrandModel(brandModel);
        return ResponseEntity.ok(Collections.singletonMap("message","Model updated successfully"));
    }

    @DeleteMapping("/id")
    public ResponseEntity<Map<String, String>> deleteBrandModel(@RequestParam(value = "id") Long id) throws ResourceNotFoundException {
        brandModelService.deleteBrandModelById(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Model deleted Successfully"));
    }



}
