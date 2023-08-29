package com.example.aspblindajes.service;

import com.example.aspblindajes.dto.AllMonthlyProductivityResponse;
import com.example.aspblindajes.dto.ProblemForModelResponse;
import com.example.aspblindajes.dto.TotalPercentageQueryResponse;
import com.example.aspblindajes.dto.WorkGroupProblemQueryResponse;
import com.example.aspblindajes.exception.InvalidArgumentException;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.WorkGroup;
import com.example.aspblindajes.model.WorkGroupProblem;
import com.example.aspblindajes.repository.WorkGroupProblemRepository;
import com.example.aspblindajes.repository.WorkGroupsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class WorkGroupProblemServiceImpl implements WorkGroupProblemService{

    private final WorkGroupProblemRepository workGroupProblemRepository;
    private final WorkGroupsRepository workGroupsRepository;

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
    public List<WorkGroupProblemQueryResponse> calculatePercentageOfProblemsForWorkGroup() throws ResourceNotFoundException {
        List<WorkGroup> workGroupList = workGroupsRepository.findAll();
        List<WorkGroupProblemQueryResponse> workGroupProblemQueryResponseList = new ArrayList<>();
        Long erroresTotales = workGroupProblemRepository.countWorkGroupProblemsWithProblem();
        if(workGroupList.size() > 0){
            for (WorkGroup workGroup : workGroupList) {
                Long erroresWG = workGroupProblemRepository.calculatePercentageOfProblemsForWorkGroup(workGroup.getName());
                WorkGroupProblemQueryResponse workGroupProblemQueryResponse = new WorkGroupProblemQueryResponse();
                workGroupProblemQueryResponse.setName(workGroup.getName());
                workGroupProblemQueryResponse.setNumeroDeErrores(erroresWG);
                workGroupProblemQueryResponse.setNumeroDeErrores(erroresTotales);
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
    public Long countWorkGroupProblemsWithProblem() throws ResourceNotFoundException {
        if (workGroupProblemRepository.findAll().size() > 0){
           return workGroupProblemRepository.countWorkGroupProblemsWithProblem();
        }
        throw new ResourceNotFoundException("there are no workGroupProblems");
    }

    @Override
    public List<ProblemForModelResponse> getProblemForModel() {
        List<ProblemForModelResponse> resultados = new ArrayList<>();
        List<Object[]> resultadosDesdeBaseDeDatos  = workGroupProblemRepository.countProblemsByModel();

        for (Object[] fila : resultadosDesdeBaseDeDatos) {
            String modelo = (String) fila[0];
            long errores = (long) fila[1];

            ProblemForModelResponse resultado = new ProblemForModelResponse(modelo, errores);
            resultados.add(resultado);
        }
        return resultados;
    }




}
