package com.example.aspblindajes.repository;
import com.example.aspblindajes.model.BrandModel;
import com.example.aspblindajes.model.WorkGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkGroupsRepository extends JpaRepository<WorkGroup, Long> {
    WorkGroup findWorkGroupsByName(String name);
}
