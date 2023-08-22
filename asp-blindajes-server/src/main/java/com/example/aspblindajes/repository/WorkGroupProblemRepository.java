package com.example.aspblindajes.repository;

import com.example.aspblindajes.model.WorkGroupProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkGroupProblemRepository extends JpaRepository <WorkGroupProblem, Long> {

    @Query("SELECT AVG(CASE WHEN wgp.hasProblem = true THEN 1.0 ELSE 0.0 END) * 100 FROM WorkGroupProblem wgp")
    double totalQtyOfProblems();


    @Query(nativeQuery = true, value = "SELECT (COUNT(CASE WHEN wp.has_problem = true THEN 1 END) * 100.0) / NULLIF((SELECT COUNT(*) FROM work_group_problem WHERE has_problem = true), 0) AS percentage FROM work_group_problem wp WHERE wp.has_problem = true AND wp.work_groups_id IN (SELECT id FROM work_group WHERE name = :workGroupName)")
    double calculatePercentageOfProblemsForWorkGroup(@Param("workGroupName") String workGroupName);


    @Query(nativeQuery = true, value = "SELECT (COUNT(CASE WHEN wp.has_problem = true THEN 1 END) * 100.0) / NULLIF((SELECT COUNT(*) FROM work_group_problem wp2 JOIN work_group wg2 ON wp2.work_groups_id = wg2.id WHERE wg2.name = :workGroupName), 0) AS percentage FROM work_group_problem wp JOIN work_group wg ON wp.work_groups_id = wg.id WHERE wg.name = :workGroupName")
    Double calculatePercentageOfProblemsInsideWorkGroup(@Param("workGroupName") String workGroup);
}
