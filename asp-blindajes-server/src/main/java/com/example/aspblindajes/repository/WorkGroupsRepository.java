package com.example.aspblindajes.repository;
import com.example.aspblindajes.model.WorkGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface WorkGroupsRepository extends JpaRepository<WorkGroups, Long> {
}
