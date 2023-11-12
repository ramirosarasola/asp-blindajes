package com.example.aspblindajes.controller;

import com.example.aspblindajes.dto.BrandModelDTO;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.BrandModel;
import com.example.aspblindajes.service.BrandModelService;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<BrandModel> saveBrandModel(@RequestBody BrandModelDTO brandModelDTO) throws ResourceAlreadyExistsException {
        return ResponseEntity.ok(brandModelService.saveBrandModel(brandModelDTO));
    }

    @GetMapping
    public ResponseEntity<BrandModel> getBrandModelByName(@RequestParam(value = "name") String name) throws ResourceNotFoundException {
        return ResponseEntity.ok(brandModelService.findBrandModelByName(name));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BrandModel>> listBrandModels() throws ResourceNotFoundException {
        return ResponseEntity.ok(brandModelService.listBrandModels());
    }

    @GetMapping("/visible")
    ResponseEntity<List<BrandModel>> listBrandModelsHiddenFalse () throws ResourceNotFoundException {
        return ResponseEntity.ok(brandModelService.listBrandModelsHiddenFalse());
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteBrandModel(@RequestParam(value = "id") Long id) throws ResourceNotFoundException {
        brandModelService.deleteBrandModelById(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Model deleted Successfully"));
    }
    @PutMapping("/hide")
    ResponseEntity<BrandModel> setHiddenBrandModel(@RequestParam(value = "id") Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(brandModelService.modelSetHidden(id));
    }

    @PutMapping
    public ResponseEntity<Map<String, String>> updateBrandModel(@RequestBody BrandModelDTO brandModelDTO) throws ResourceNotFoundException {
        brandModelService.updateBrandModel(brandModelDTO);
        return ResponseEntity.ok(Collections.singletonMap("message","Model updated successfully"));
    }
}
