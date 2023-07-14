package com.example.aspblindajes.repository;
import com.example.aspblindajes.model.BrandModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BrandModelRepository extends JpaRepository<BrandModel, Long> {
    Optional<BrandModel> findBrandModelByName(String name);
}
