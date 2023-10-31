package com.example.aspblindajes.service;

import com.example.aspblindajes.converters.WorkGroupProblemToResponseWGPFilterConverter;
import com.example.aspblindajes.dto.*;
import com.example.aspblindajes.exception.InvalidArgumentException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.VehicleMovement;
import com.example.aspblindajes.model.WorkGroup;
import com.example.aspblindajes.model.WorkGroupProblem;
import com.example.aspblindajes.repository.WorkGroupProblemRepository;
import com.example.aspblindajes.repository.WorkGroupsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class WorkGroupProblemServiceImpl implements WorkGroupProblemService{

    private final WorkGroupProblemRepository workGroupProblemRepository;
    private final WorkGroupsRepository workGroupsRepository;
    private final WorkGroupProblemToResponseWGPFilterConverter wgpFilterConverter;

    @Override
    public WorkGroupProblem getWorkGroupProblemById(Long id) throws ResourceNotFoundException {
       Optional<WorkGroupProblem> workGroupProblem = workGroupProblemRepository.findById(id);
       if (workGroupProblem.isPresent()){
           return workGroupProblem.get();
        }
       throw new ResourceNotFoundException("There are no workGroupProblems with the provided id");
    }

    @Override
    public List<WorkGroupProblem> findAllWorkGroupProblems() throws ResourceNotFoundException {
        List<WorkGroupProblem> workGroupProblemList = workGroupProblemRepository.findAll();
        if (workGroupProblemList.size() > 0){
            return workGroupProblemList;
        }
        throw new ResourceNotFoundException("There are no workGroupsProblems in the database");
    }

    @Override
    public WorkGroupProblem updateWorkGroupProblem(WorkGroupProblem workGroupProblem) throws InvalidArgumentException {
        Optional<WorkGroupProblem> workGroupProblem1 = workGroupProblemRepository.findById(workGroupProblem.getId());
        if (workGroupProblem1.isPresent()){
            return workGroupProblemRepository.save(workGroupProblem);
        }
        throw new InvalidArgumentException("The provided id does not match with any workGroup registered in the dataBase");
    }

    public void deleteWGPById (Long id) throws ResourceNotFoundException{
        Optional<WorkGroupProblem> optionalWGP = workGroupProblemRepository.findById(id);
        if (optionalWGP.isEmpty()){
            log.error("Fail to delete work group problem: There are no workGroupsProblems with the provided id");
            throw new ResourceNotFoundException("There are no workGroupsProblem with the provided id");
        }
        log.info("Work group problem deleted successfully");
        workGroupProblemRepository.deleteById(id);
    };

    @Override
    public TotalPercentageQueryResponse getPercentageOfProblems() throws ResourceNotFoundException {
        TotalPercentageQueryResponse totalPercentageQueryResponse = new TotalPercentageQueryResponse();
        if(workGroupProblemRepository.findAll().size() > 0){
            totalPercentageQueryResponse.setPorcentajeTotalDeProblemasDeGruposDeTrabajoControlados(workGroupProblemRepository.totalQtyOfProblems());
            totalPercentageQueryResponse.setCantidadTotalDeGruposDeTrabajoControlados(workGroupProblemRepository.countTotalWorkGroupProblems());
            return totalPercentageQueryResponse;
        }
        throw new ResourceNotFoundException("there are no workGroupProblems to get the percentage of");
    }

    @Override
    public List<WorkGroupProblemQueryResponse> calculatePercentageOfProblemsForWorkGroup(int mes) throws ResourceNotFoundException {
        List<WorkGroup> workGroupList = workGroupsRepository.findAll();
        List<WorkGroupProblemQueryResponse> workGroupProblemQueryResponseList = new ArrayList<>();
        Long erroresTotales = workGroupProblemRepository.countWorkGroupProblemsWithProblem(mes);
        if(workGroupList.size() > 0){
            for (WorkGroup workGroup : workGroupList) {
                Long erroresWG = workGroupProblemRepository.calculatePercentageOfProblemsForWorkGroup(workGroup.getName(), mes);
                WorkGroupProblemQueryResponse workGroupProblemQueryResponse = new WorkGroupProblemQueryResponse();
                workGroupProblemQueryResponse.setName(workGroup.getName());
                workGroupProblemQueryResponse.setNumeroDeErrores(erroresWG);
                workGroupProblemQueryResponse.setCantidadDeControles(erroresTotales);
                if(erroresWG != 0 && erroresTotales != 0){
                    workGroupProblemQueryResponse.setPorcentaje((double) erroresWG / erroresTotales * 100);
                } else {
                    workGroupProblemQueryResponse.setPorcentaje(0);
                }
                workGroupProblemQueryResponseList.add(workGroupProblemQueryResponse);
            }

            return workGroupProblemQueryResponseList;
        }
        throw new ResourceNotFoundException("there are no workGroupProblems to get the percentage of");
    }

//    @Override
//    public List<WorkGroupProblemQueryResponse> calculatePercentageOfProblemsInsideWorkGroup() throws ResourceNotFoundException {
//        List<WorkGroup> workGroupList = workGroupsRepository.findAll();
//        List<WorkGroupProblemQueryResponse> workGroupProblemQueryResponseList = new ArrayList<>();
//        if (workGroupList.size() > 0) {
//            for (WorkGroup workGroup : workGroupList) {
//                Long problemas = workGroupProblemRepository.calculatePercentageOfProblemsInsideWorkGroup(workGroup.getName());
//                Long cantidad = workGroupProblemRepository.countWorkGroupProblemsForGroupName(workGroup.getName());
//
//                WorkGroupProblemQueryResponse workGroupProblemQueryResponse = new WorkGroupProblemQueryResponse();
//                workGroupProblemQueryResponse.setName(workGroup.getName());
//                workGroupProblemQueryResponse.setNumeroDeErrores(problemas);
//                workGroupProblemQueryResponse.setCantidadDeControles(cantidad);
//                if (problemas != 0 && cantidad != 0){
//                    workGroupProblemQueryResponse.setPorcentaje((double) problemas /cantidad*100);
//                } else {
//                    workGroupProblemQueryResponse.setPorcentaje(0);
//                }
//                workGroupProblemQueryResponseList.add(workGroupProblemQueryResponse);
//            }
//            return workGroupProblemQueryResponseList;
//        }
//        throw new ResourceNotFoundException("there are no workGroupProblems to get the percentage of");
//    }

    @Override
    public Long countWorkGroupProblemsWithProblem(int mes) throws ResourceNotFoundException {
        if (workGroupProblemRepository.findAll().size() > 0){
           return workGroupProblemRepository.countWorkGroupProblemsWithProblem(mes);
        }
        throw new ResourceNotFoundException("there are no workGroupProblems");
    }

    @Override
    public List<ProblemForModelResponse> getProblemForModel(int mes) {
        List<ProblemForModelResponse> resultados = new ArrayList<>();
        List<Object[]> resultadosDesdeBaseDeDatos  = workGroupProblemRepository.countProblemsByModel(mes);

        for (Object[] fila : resultadosDesdeBaseDeDatos) {
            String modelo = (String) fila[0];
            long errores = (long) fila[1];
            Long problems = workGroupProblemRepository.countWorkGroupProblemsWithProblem(mes);
            double porcentaje = 0.0;
            if (errores != 0 && problems != 0){
                porcentaje = ((double) errores / problems * 100);
            }
            ProblemForModelResponse resultado = new ProblemForModelResponse(modelo, errores, porcentaje);
            resultados.add(resultado);
        }
        return resultados;
    }

    @Override
    public List<ResponseWGPFilter> getWorkGroupProblemsByFilter(String chasis, String workGroup, String startDate, String endDate) {

        List<ResponseWGPFilter> responseWGPFilters = new ArrayList<>();
        if (chasis == null && workGroup == null && startDate == null && endDate == null) {
            List<WorkGroupProblem> workGroupProblemList = workGroupProblemRepository.findAll();
            for (WorkGroupProblem workGroupProblem : workGroupProblemList) {
                responseWGPFilters.add(wgpFilterConverter.convert(workGroupProblem));
            }
        }else {
            List<WorkGroupProblem> workGroupProblemList = workGroupProblemRepository.getWGPByFilters(chasis, workGroup, dateStartConverter(startDate), dateEndConverter(endDate));
            for (WorkGroupProblem workGroupProblem : workGroupProblemList) {
                responseWGPFilters.add(wgpFilterConverter.convert(workGroupProblem));
            }
        }
        return responseWGPFilters;


    }

    private LocalDateTime dateStartConverter(String date) {
        if (date != null && !date.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, formatter).atStartOfDay();
        } else {
            return null;
        }
    }

    private LocalDateTime dateEndConverter(String date) {
        if (date != null && !date.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, formatter).atTime(23, 59);
        } else {
            return null;
        }
    }


}
