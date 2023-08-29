package com.example.aspblindajes.repository;

import com.example.aspblindajes.model.WorkGroupProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkGroupProblemRepository extends JpaRepository <WorkGroupProblem, Long> {

    @Query("SELECT AVG(CASE WHEN wgp.hasProblem = true THEN 1.0 ELSE 0.0 END) * 100 FROM WorkGroupProblem wgp")
    double totalQtyOfProblems();

    @Query ("SELECT COUNT(wgp.id) FROM WorkGroupProblem wgp")
    Long countTotalWorkGroupProblems();


    @Query(nativeQuery = true, value = "SELECT COUNT(*) AS count FROM work_group_problem wp JOIN work_group wg ON wp.work_groups_id = wg.id WHERE wp.has_problem = 1 AND wg.name = :workGroupName")
    Long calculatePercentageOfProblemsForWorkGroup(@Param("workGroupName") String workGroup);

    @Query(value = "SELECT COUNT(wgp) FROM WorkGroupProblem wgp WHERE wgp.hasProblem = true")
    Long countWorkGroupProblemsWithProblem();

//    @Query(value = "SELECT COUNT(wgp.id) FROM WorkGroup wg INNER JOIN WorkGroupProblem wgp ON wg.id = wgp.workGroup.id WHERE wg.name = :groupName")
//    Long countWorkGroupProblemsForGroupName(@Param("groupName") String groupName);

    @Query(nativeQuery = true, value = "SELECT bm.name AS model_name, COUNT(*) AS problem_count "
            + "FROM work_group_problem wp "
            + "JOIN vehicle_quality_control vqc ON wp.vehicle_quality_control_id = vqc.id "
            + "JOIN vehicle v ON vqc.vehicle_id = v.id "
            + "JOIN brand_model bm ON v.brand_model_id = bm.id "
            + "WHERE wp.has_problem = 1 "
            + "GROUP BY bm.name")
    List<Object[]> countProblemsByModel();

}
