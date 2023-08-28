package com.example.aspblindajes.service;

import com.example.aspblindajes.converters.VehicleDTOToVehicleConverter;
import com.example.aspblindajes.dto.MonthlyProductivityResponse;
import com.example.aspblindajes.dto.VehicleDTO;
import com.example.aspblindajes.dto.VehiclesPerAreaQueryResponse;
import com.example.aspblindajes.exception.InvalidArgumentException;
import com.example.aspblindajes.exception.ResourceAlreadyExistsException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.Area;
import com.example.aspblindajes.model.MovementType;
import com.example.aspblindajes.model.Vehicle;
import com.example.aspblindajes.model.VehicleMovement;
import com.example.aspblindajes.repository.UserRepository;
import com.example.aspblindajes.repository.VehicleMovementRepository;
import com.example.aspblindajes.repository.VehicleRepository;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class VehicleServiceImpl implements VehicleService{

    private final VehicleRepository vehicleRepository;
    private final VehicleDTOToVehicleConverter vehicleDTOToVehicleConverter;
    private final VehicleMovementRepository vehicleMovementRepository;
    private final UserRepository userRepository;
    @Override
    public Vehicle saveVehicle(VehicleDTO vehicleDTO, String userName) throws ResourceAlreadyExistsException {
       Vehicle vehicle = vehicleDTOToVehicleConverter.convert(vehicleDTO);
       vehicle.setId(generateId(vehicleDTO.getChasis()));

       if(vehicleRepository.findById(vehicleDTO.getChasis()).isEmpty() && vehicle != null){

           if(Objects.equals(vehicleDTO.getBrandName(), "Ford") && vehicleDTO.getFordKey().isEmpty()){
               log.error("Fail to save vehicle: Ford's vehicles must have their unique key");
               throw new ResourceAlreadyExistsException("Ford's vehicles must have their unique key"); //todo -> change exception
           }
           VehicleMovement vehicleMovement = new VehicleMovement();
           vehicleMovement.setUser(userRepository.findUserByUsername(userName).get());
           vehicleMovement.setVehicle(vehicle);
           vehicleMovement.setMovementType(MovementType.LOGISTIC_CHECKIN);

           vehicleRepository.save(vehicle);
           vehicleMovementRepository.save(vehicleMovement);
           log.info("Vehicle saved successfully");
           return vehicle;
       }
       log.error("Fail to save vehicle: There is a vehicle with the chasis provided");
        throw new ResourceAlreadyExistsException("There is a vehicle with the chasis provided");
    }

    @Override
    public Vehicle findVehicleById(String id) throws ResourceNotFoundException {
        if(vehicleRepository.findById(id).isPresent()){
            log.info("Vehicle found by ID");
            return vehicleRepository.findById(id).get();
        }
        log.error("Fail to find vehicle by chasis: The vehicle could not be found by the provided chasis");
        throw new ResourceNotFoundException("The vehicle could not be found by the provided chasis");
    }

    @Override
    public void deleteVehicleByChasis(String chasis) throws ResourceNotFoundException {
        if(vehicleRepository.findVehicleByChasis(chasis) == null){
            log.error("Fail to delete vehicle: There is no vehicle with the provided ID (chasis) ");
            throw new ResourceNotFoundException("There is no vehicle with the provided ID (chasis)");
        }
        log.info("Vehicle deleted successfully CHASIS: " + chasis);
        vehicleRepository.deleteVehicleByChasis(chasis);
    }

    @Override
    public List<Vehicle> findAllVehicles() throws ResourceNotFoundException {
        if (vehicleRepository.findAll().size() > 0){
            log.info("All vehicles founded successfully");
            return vehicleRepository.findAll();
        }
        log.error("Fail to list all vehicles: There are no vehicle on the database");
        throw new ResourceNotFoundException("There are no vehicle on the database");
    }

    @Override
    public Vehicle updateVehicle(VehicleDTO vehicleDTO) throws ResourceNotFoundException {
        Vehicle vehicle = vehicleDTOToVehicleConverter.convert(vehicleDTO);

        if(vehicleRepository.findById(vehicleDTO.getChasis()).isPresent() && vehicle != null){
            log.info("Vehicle updated successfully");
            return vehicleRepository.save(vehicle);
        }
        log.error("Fail to update vehicle: The vehicle trying to update doesn't exists. ");
        throw new ResourceNotFoundException("The vehicle trying to update doesn't exists.");
    }

    @Override
    public Vehicle updateVehicleAreaByMovementType(Area area, String chasis) throws ResourceNotFoundException {
        Optional<Vehicle> optionalVehicle = Optional.of(vehicleRepository.findVehicleByChasis(chasis));
        if(optionalVehicle.isPresent()){
            Vehicle vehicle = optionalVehicle.get();
            vehicle.setArea(area);
            log.info("Vehicle area updated sucessfully");
            return vehicleRepository.save(vehicle);
        }
        log.error("Fail to update the vehicle area: The vehicle trying to update doesn't exists. ");
        throw new ResourceNotFoundException("The vehicle trying to update doesn't exists.");
    }

    @Override
    public Vehicle findVehicleByChasis(String chasis) throws ResourceNotFoundException {
        Vehicle vehicle = vehicleRepository.findVehicleByChasis(chasis);
       if (vehicle != null){
           return vehicle;
       }
       throw new ResourceNotFoundException("There is no vehicle with the provided chasis");
    }

    @Override
    public List<VehiclesPerAreaQueryResponse> getAmoutOfVehiclesPerArea()  {
        List<VehiclesPerAreaQueryResponse> vehiclesPerAreaQueryResponseList = new ArrayList<>();
        VehiclesPerAreaQueryResponse vehiclesPerAreaQueryResponse = new VehiclesPerAreaQueryResponse();
        VehiclesPerAreaQueryResponse vehiclesPerAreaQueryResponse1 = new VehiclesPerAreaQueryResponse();
        VehiclesPerAreaQueryResponse vehiclesPerAreaQueryResponse2 = new VehiclesPerAreaQueryResponse();
       vehiclesPerAreaQueryResponse.setName("Logistica sin control de calidad");
       vehiclesPerAreaQueryResponse.setVehiculosEnArea(vehicleRepository.countVehiclesInLogisticAreaWithCanBeCheckedOutFalse());
        vehiclesPerAreaQueryResponse1.setName("Produccion");
        vehiclesPerAreaQueryResponse1.setVehiculosEnArea(vehicleRepository.countVehiclesInProductionArea());
        vehiclesPerAreaQueryResponse2.setName("Logistica con control de calidad");
        vehiclesPerAreaQueryResponse2.setVehiculosEnArea(vehicleRepository.countVehiclesInLogisticAreaWithCanBeCheckedOutTrue());
        vehiclesPerAreaQueryResponseList.add(vehiclesPerAreaQueryResponse);
        vehiclesPerAreaQueryResponseList.add(vehiclesPerAreaQueryResponse1);
        vehiclesPerAreaQueryResponseList.add(vehiclesPerAreaQueryResponse2);

        return vehiclesPerAreaQueryResponseList;

    }

    @Override
    public MonthlyProductivityResponse monthlyProductivity() {
        MonthlyProductivityResponse monthlyProductivityResponse = new MonthlyProductivityResponse();

        LocalDate currentDate = LocalDate.now();

        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();

        LocalDate previousMonthDate = currentDate.minusMonths(1);
        int previousMonth = previousMonthDate.getMonthValue();
        int previousYear = previousMonthDate.getYear();

        monthlyProductivityResponse.setProductividadActual(vehicleRepository.monthlyProductivity(currentMonth, currentYear));
        monthlyProductivityResponse.setProductividadPasada(vehicleRepository.monthlyProductivity(previousMonth, previousYear));
        return monthlyProductivityResponse;
    }

    @Override
    public MonthlyProductivityResponse weeklyProductivity() {
        LocalDate currentDate = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        int currentYear = currentDate.getYear();
        int currentWeekOfYear = currentDate.get(weekFields.weekOfWeekBasedYear());

        LocalDate previousWeekDate = currentDate.minusWeeks(1);
        int previousYear = previousWeekDate.getYear();
        int previousWeekOfYear = previousWeekDate.get(weekFields.weekOfWeekBasedYear());

        MonthlyProductivityResponse monthlyProductivityResponse = new MonthlyProductivityResponse();
        monthlyProductivityResponse.setProductividadActual(vehicleRepository.weeklyProductivity(currentYear, currentWeekOfYear));
        monthlyProductivityResponse.setProductividadActual(vehicleRepository.weeklyProductivity(previousYear, previousWeekOfYear));

        return monthlyProductivityResponse;

    }


    private String generateId(String chasis) {
        String digits = chasis.substring(chasis.length() - 8);
        return digits;
    }

}
