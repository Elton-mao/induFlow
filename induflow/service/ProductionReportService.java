package com.compoldata.induflow.service;

import com.compoldata.induflow.model.ProductionOrder;
import com.compoldata.induflow.model.ProductionOrderLog;
import com.compoldata.induflow.DTO.response.ProductionReportItemDTO;
import com.compoldata.induflow.model.Ocurrence;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductionReportService {

    public List<com.compoldata.induflow.DTO.response.ProductionReportItemDTO> buildReportItems(ProductionOrder productionOrder) {
        List<ProductionReportItemDTO> reportItems = new ArrayList<>();

        // Logs
        for (ProductionOrderLog log : productionOrder.getProductionOrderLogs()) {
            reportItems.add(ProductionReportItemDTO.builder()
                    .type("LOG")
                    .dateTime(log.getRecordedTime())
                    .logMessage(log.getLogmessage())
                    .producedAmount(log.getProducedAmount())
                    .employeeName(log.getEmployeeName())
                    .build());
        }

        // Occurrences
        for (Ocurrence oc : productionOrder.getOcurrences()) {
            reportItems.add(ProductionReportItemDTO.builder()
                    .type("OCURRENCE")
                    .dateTime(oc.getStartOcurrenceLocalDateTime())
                    .observation(oc.getObservation())
                    .employee(oc.getEmployee() != null ? oc.getEmployee().getEmployeeName() : "")
                    .department(oc.getDepartment() != null ? oc.getDepartment().getDepartmentName() : "")
                    .stopCode(oc.getStopCodes())
                    .affectedEquipment(oc.getAffectedEquipment())
                    .status(oc.getStatus())
                    .occurrenceType(oc.getOcorrenceType())
                    .shift(oc.getShift())
                    .build());
        }

        // Ordenar por data/hora
        reportItems.sort(Comparator.comparing(ProductionReportItemDTO::getDateTime));

        return reportItems;
    }
}
