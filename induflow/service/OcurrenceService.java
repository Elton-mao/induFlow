package com.compoldata.induflow.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.compoldata.induflow.DTO.request.OccurrenceForm;
import com.compoldata.induflow.DTO.response.ToOcurrence;
import com.compoldata.induflow.exceptions.OccurrenceNotFoundException;
import com.compoldata.induflow.exceptions.ProductionLineNotFoundException;
import com.compoldata.induflow.exceptions.ProductionOrderNotFoundException;
import com.compoldata.induflow.model.Ocurrence;
import com.compoldata.induflow.model.ProductionOrder;
import com.compoldata.induflow.model.enums.AffectedEquipment;
import com.compoldata.induflow.model.enums.OccurrenceStatus;
import com.compoldata.induflow.model.enums.OccurrenceType;
import com.compoldata.induflow.model.enums.ProductionOrderStatus;
import com.compoldata.induflow.model.enums.Shift;
import com.compoldata.induflow.model.enums.StopCodes;
import com.compoldata.induflow.repository.appRepositories.OcurrenceRepository;

@Service
public class OcurrenceService {
    @Autowired
    private OcurrenceRepository ocurrenceRepository;

    @Autowired
    private ProductionOrderService productionOrderService;

    @Autowired 
    private ProductionOrderLogService productioOrderLogService; 

    @Autowired
    private AutheticationService autheticationService; 
    

    //envia detalhes da ocorrência
    public ToOcurrence ocurrenceDetails(Long ocurrenceId) {
        return ocurrenceRepository.findById(ocurrenceId)
                .map(ToOcurrence::toOcurrence)
                .orElseThrow(OccurrenceNotFoundException::new);
    }

    // registra uma nova ocorrência
    // recebe um DTO do controller, setta a ordem de produção para PARADO
    @Transactional(transactionManager = "appTransactionManager")
    public void registerOcurrence(OccurrenceForm ocurrenceDTO) {
        
        // Converte o DTO para a entidade
        if (ocurrenceDTO.ProductionOrderId() == null) {
            throw new IllegalArgumentException("id da ordem de produção está nulo");
        }
        var employeeAtheticated = autheticationService.getAutheticatedUser(); 

        ProductionOrder productionOrder = productionOrderService
                .productionOrderInformation(ocurrenceDTO.ProductionOrderId());
        Ocurrence newOcurrence = new Ocurrence();
        // verifica o turno da ocorrência
        newOcurrence.setShift(identifyShift(LocalDateTime.now()));
        // DEFINE O STATUS DA ORDEM DE PRODUÇÃO PARA PARADO
        productionOrderService.handleProductionOrderStop(productionOrder.getProductionOrderId());

        newOcurrence.setStartOcurrenceLocalDateTime(LocalDateTime.now());

        newOcurrence.setObservation(ocurrenceDTO.Observation());

        newOcurrence.setEmployee(employeeAtheticated);

        newOcurrence.setDepartment(employeeAtheticated.getEmployeeDepartment());

        newOcurrence.setStopCodes(ocurrenceDTO.stopCodes());

        newOcurrence.setAffectedEquipment(ocurrenceDTO.affectedEquipment());

        newOcurrence.setOcorrenceType(ocurrenceDTO.ocorrenceType());

        newOcurrence.setStatus(OccurrenceStatus.ATIVO);
        
        newOcurrence.setProductionOrder(productionOrder);
        //registra um log de operação 
        productioOrderLogService.registerProductionOrderLog(productionOrder, "Ocorrência Registrada",BigDecimal.ZERO,
        employeeAtheticated.getEmployeeName());
        // Salva no banco
        ocurrenceRepository.save(newOcurrence);

    }

    // Finaliza Ocorrência
    @Transactional(transactionManager = "appTransactionManager")
    public void endOcurrence(Long ocurrenceId) {
        Ocurrence ocurrence = ocurrenceRepository.findById(ocurrenceId)
                .orElseThrow(OccurrenceNotFoundException::new);

        if (ocurrence.getStatus() == OccurrenceStatus.ATIVO) {
            ocurrence.setEndOcurrenceLocalDateTime(LocalDateTime.now());
            // DEFINE A ORDEM DE PRODUÇÃO PARA PRODUZINDO
            productionOrderService.startProductionOrder(ocurrence.getProductionOrder().getProductionOrderId());
            // altera o estado da ocurrencia para encerrada
            ocurrence.setStatus(OccurrenceStatus.FINALIZADO);
            // salva a ocorrencia
            ocurrenceRepository.save(ocurrence);
        } else {
            throw new OccurrenceNotFoundException(
                    ("A ocorrência já foi finalizada"));

        }

    }

    // lista todas ocorrencias cadastradas no sistema
    public List<ToOcurrence> findAllOcurrences() {

        return ocurrenceRepository.findAll().stream()
                .map(ToOcurrence::toOcurrence)
                .toList();

    }

    // Inicia o Setup da Linha de produção
    @Transactional(transactionManager = "appTransactionManager")
    public void startSetupOccurrence(OccurrenceForm occurrenceForm) {     

        //valida se o id da ordem de produção está presente no formulario
        if (occurrenceForm.ProductionOrderId() == null) {
            throw new ProductionOrderNotFoundException("id da ordem de produção está nulo");
        }

        //buusca ordem de produção requisitada no formulario
        ProductionOrder productionOrder = productionOrderService
                .productionOrderInformation(occurrenceForm.ProductionOrderId());

        //valida se a linha de produção está livre para iniciar o SETUP
        if (productionOrder.getProductionLine().isRunning()) {
            throw new ProductionLineNotFoundException(productionOrder.getProductionLine().getProductionLineNumber());
        }

        Ocurrence newOcurrence = new Ocurrence();

        // valida se a ordem de produção já esta em SETUP
        if (productionOrder.getStatus() == ProductionOrderStatus.SETUP) {
            throw new ProductionOrderNotFoundException("Setup Já iniciado");
        }

        var employeeAtheticated = autheticationService.getAutheticatedUser(); 
        
        productionOrder.setStatus(ProductionOrderStatus.SETUP);
        productionOrder.getProductionLine().setRunning(true);
        newOcurrence.setShift(identifyShift(LocalDateTime.now()));
        newOcurrence.setStartOcurrenceLocalDateTime(LocalDateTime.now());
        newOcurrence.setObservation("Preparando o processo produtivo conforme os requisitos da ordem de produção.");
        newOcurrence.setEmployee(employeeAtheticated);
        newOcurrence.setDepartment( employeeAtheticated.getEmployeeDepartment());
        newOcurrence.setStopCodes(StopCodes.CONFIGURACAO_MANUTENCAO);
        newOcurrence.setAffectedEquipment(AffectedEquipment.LINHA);
        newOcurrence.setOcorrenceType(OccurrenceType.PREPARAÇÃO);
        newOcurrence.setStatus(OccurrenceStatus.ATIVO);
        
        
        newOcurrence.setProductionOrder(productionOrder);
        
        // Salva no banco
        ocurrenceRepository.save(newOcurrence);

    }

    // finaliza o SETUP
    @Transactional(transactionManager = "appTransactionManager")
    public void endSetupOccurrence(Long ocurrenceId) {
        Ocurrence ocurrence = ocurrenceRepository.findById(ocurrenceId)
                .orElseThrow(OccurrenceNotFoundException::new);
        if (ocurrence.getOcorrenceType() != OccurrenceType.PREPARAÇÃO) {
            throw new OccurrenceNotFoundException("Ocorrencia Não é um Setup");
        }
        if (ocurrence.getStatus() == OccurrenceStatus.ATIVO) {

            // Define data e hora de encerramento da ocorrência
            ocurrence.setEndOcurrenceLocalDateTime(LocalDateTime.now());

            // Retoma a Produção
            productionOrderService.startProductionOrder(ocurrence.getProductionOrder().getProductionOrderId());
            ocurrence.getProductionOrder().setStatus(ProductionOrderStatus.PRODUZINDO);

            // Define a ocorrência como finalizada
            ocurrence.setStatus(OccurrenceStatus.FINALIZADO);

            // Captura o horário inicial da produção da OP
            if (ocurrence.getProductionOrder().getStartProductionDateTime() == null) {
                ocurrence.getProductionOrder().setStartProductionDateTime(LocalDateTime.now());
            }

            // Define a linha de produção como ocupada
            if (!ocurrence.getProductionOrder().getProductionLine().isRunning()) {
                ocurrence.getProductionOrder().getProductionLine().setRunning(true);
            }

            // Salva as alterações no banco
            ocurrenceRepository.save(ocurrence);
        } else {
            throw new OccurrenceNotFoundException(String.format(
                    "Setup Já está Finalizado: %s",
                    ocurrence.getEndOcurrenceLocalDateTime()));
        }
    }

    // indentifica o turno
    private Shift identifyShift(LocalDateTime startOccurrenceLocalDateTime) {
        LocalTime endOfFirstShift = LocalTime.of(15, 0);
        LocalTime occurrenceTime = startOccurrenceLocalDateTime.toLocalTime();

        if (!occurrenceTime.isAfter(endOfFirstShift)) {
            return Shift.PRIMEIRO_TURNO;
        } else {
            return Shift.SEGUNDO_TURNO;
        }
    }

}
