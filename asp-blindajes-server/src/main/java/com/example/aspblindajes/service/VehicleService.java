package com.example.aspblindajes.service;

import com.example.aspblindajes.dto.AllMonthlyProductivityResponse;
import com.example.aspblindajes.dto.MonthlyProductivityResponse;
import com.example.aspblindajes.dto.VehicleDTO;
import com.example.aspblindajes.dto.VehiclesPerAreaQueryResponse;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Area;
import com.example.aspblindajes.model.MovementType;
import com.example.aspblindajes.model.Vehicle;

import java.util.List;

public interface VehicleService {
    Vehicle saveVehicle (VehicleDTO vehicleDTO, String userName) throws ResourceAlreadyExistsException;
    Vehicle findVehicleById(String id) throws ResourceNotFoundException;

    void deleteVehicleByChasis(String id) throws ResourceNotFoundException;
    List<Vehicle> findAllVehicles() throws ResourceNotFoundException;

    Vehicle updateVehicle (VehicleDTO vehicleDTO) throws ResourceNotFoundException;

    Vehicle updateVehicleAreaByMovementType (Area area, String chasis) throws ResourceNotFoundException;

    Vehicle findVehicleByChasis (String chasis) throws ResourceNotFoundException ;

    List<VehiclesPerAreaQueryResponse> getAmoutOfVehiclesPerArea ();

    MonthlyProductivityResponse monthlyProductivity();
//    MonthlyProductivityResponse weeklyProductivity();

    List<AllMonthlyProductivityResponse> allMonthlyProductivity(int year);

}
