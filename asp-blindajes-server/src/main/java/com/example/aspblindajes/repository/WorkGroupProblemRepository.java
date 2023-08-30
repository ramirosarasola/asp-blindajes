package com.example.aspblindajes.repository;

import com.example.aspblindajes.model.WorkGroupProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface WorkGroupProblemRepository extends JpaRepository <WorkGroupProblem, Long> {

    @Query("SELECT AVG(CASE WHEN wgp.hasProblem = true THEN 1.0 ELSE 0.0 END) * 100 FROM WorkGroupProblem wgp")
    double totalQtyOfProblems();

    @Query ("SELECT COUNT(wgp.id) FROM WorkGroupProblem wgp")
    Long countTotalWorkGroupProblems();


    @Query(nativeQuery = true, value = "SELECT COUNT(wp.id) FROM work_group_problem wp " +
            "JOIN work_group wg ON wp.work_groups_id = wg.id " +
            "JOIN vehicle_quality_control vqc ON wp.vehicle_quality_control_id = vqc.id " +
            "WHERE wp.has_problem = 1 " +
            "AND wg.name = :workGroupName "+
            "AND MONTH(vqc.quality_control_date) = :mesParametro")
    Long calculatePercentageOfProblemsForWorkGroup(@Param("workGroupName") String workGroup, @Param("mesParametro") int mesParametro);

    @Query(nativeQuery = true, value = "SELECT COUNT(wgp.id) FROM work_group_problem wgp " +
            "JOIN vehicle_quality_control vqc ON vqc.id = wgp.vehicle_quality_control_id " +
            "WHERE wgp.has_problem = true " +
            "AND MONTH(vqc.quality_control_date) = :mesParametro")
    Long countWorkGroupProblemsWithProblem(@Param("mesParametro") int mesParametro);

//    @Query(value = "SELECT COUNT(wgp.id) FROM WorkGroup wg INNER JOIN WorkGroupProblem wgp ON wg.id = wgp.workGroup.id WHERE wg.name = :groupName")
//    Long countWorkGroupProblemsForGroupName(@Param("groupName") String groupName);

    @Query(nativeQuery = true, value = "SELECT bm.name AS model_name, COUNT(*) AS problem_count "
            + "FROM work_group_problem wp "
            + "JOIN vehicle_quality_control vqc ON wp.vehicle_quality_control_id = vqc.id "
            + "JOIN vehicle v ON vqc.vehicle_id = v.id "
            + "JOIN brand_model bm ON v.brand_model_id = bm.id "
            + "WHERE wp.has_problem = 1 "
            + "AND MONTH(vqc.quality_control_date) = :mesParametro "
            + "GROUP BY bm.name")
    List<Object[]> countProblemsByModel(@Param("mesParametro") int mesParametro);

    @Query(nativeQuery = true, value = "SELECT wgp.* FROM work_group_problem wgp " +
            "JOIN vehicle_quality_control vqc ON wgp.vehicle_quality_control_id = vqc.id " +
            "JOIN vehicle v ON vqc.vehicle_id = v.id " +
            "JOIN work_group wg ON wgp.work_groups_id = wg.id " +
            "WHERE (:chasis IS NULL OR v.chasis = :chasis) " +
            "AND wgp.has_problem = 1 " +
            "AND (:workGroupName IS NULL OR wg.name = :workGroupName) " +
            "AND (:startDate IS NULL OR vqc.quality_control_date >= :startDate) " +
            "AND (:endDate IS NULL OR vqc.quality_control_date <= :endDate)" )
    List<WorkGroupProblem> getWGPByFilters (@Param ("chasis") String chasis,@Param("workGroupName") String workGroup, @Param(value = "startDate") LocalDateTime startDate, @Param(value = "endDate") LocalDateTime endDate);


//    @Query(nativeQuery = true, value ="")



}
