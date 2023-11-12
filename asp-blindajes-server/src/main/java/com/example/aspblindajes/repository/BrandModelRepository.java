package com.example.aspblindajes.repository;
import com.example.aspblindajes.dto.BrandModelDTO;
import com.example.aspblindajes.model.BrandModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface BrandModelRepository extends JpaRepository<BrandModel, Long> {
    Optional<BrandModel> findBrandModelByName(String name);

    @Query (nativeQuery = true,
            value = "SELECT bm.* FROM brand_model bm " +
            "WHERE bm.hidden = 0")
    List<BrandModel> findAllBrandModelHiddenFalse();
}
