package com.example.aspblindajes.repository;
import com.example.aspblindajes.model.Brand;
import com.example.aspblindajes.model.BrandModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findBrandByName(String name);

    @Query(nativeQuery = true,
            value = "SELECT b.* FROM brand b " +
                    "WHERE b.hidden = 0")
    List<Brand> findAllBrandHiddenFalse();
}
