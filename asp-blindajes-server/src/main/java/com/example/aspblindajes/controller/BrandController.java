package com.example.aspblindajes.controller;

import com.example.aspblindajes.dto.BrandDTO;
import com.example.aspblindajes.exception.InvalidArgumentException;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Brand;
import com.example.aspblindajes.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/brand")
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<Brand> saveBrand(@RequestBody BrandDTO brandDTO) throws ResourceAlreadyExistsException, InvalidArgumentException {
        return ResponseEntity.ok(brandService.saveBrand(brandDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Brand>> findAllBrands() throws ResourceNotFoundException {
        return ResponseEntity.ok(brandService.findAllBrands());
    }

    @PutMapping
    public ResponseEntity<Brand> updateBrand(@RequestBody BrandDTO brandDTO) throws ResourceNotFoundException{
        return ResponseEntity.ok(brandService.updateBrand(brandDTO));
    }

    @GetMapping
    public ResponseEntity<Brand> findBrandById(@RequestParam(value = "id") Long id ) throws ResourceNotFoundException{
        return ResponseEntity.ok(brandService.findBrandById(id));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteBrandById(@RequestParam (value = "id") Long id) throws ResourceNotFoundException{
        brandService.deleteBrandById(id);
        return ResponseEntity.ok("Brand deleted successfully");
    }

}
