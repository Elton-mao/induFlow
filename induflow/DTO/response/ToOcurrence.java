package com.compoldata.induflow.DTO.response;

import java.time.LocalDateTime;

import com.compoldata.induflow.model.Ocurrence;
import com.compoldata.induflow.model.enums.AffectedEquipment;
import com.compoldata.induflow.model.enums.OccurrenceType;
import com.compoldata.induflow.model.enums.Shift;
import com.compoldata.induflow.model.enums.OccurrenceStatus;
import com.compoldata.induflow.model.enums.StopCodes;

public record ToOcurrence(Long ocurrenceId,LocalDateTime startOcurrenceLocalDateTime, LocalDateTime endOcurrenceLocalDateTime,
        String observation, String employee, String deparment, StopCodes stopCode,
        AffectedEquipment affectedEquipment, String productionOrderNumber, OccurrenceType
         ocorrenceType ,OccurrenceStatus ocurrenceStatus,
        Shift shift,Integer productionLineNumber , String description) {
    
            public static ToOcurrence toOcurrence(Ocurrence ocurrence){
                    return new ToOcurrence(
                        ocurrence.getOcurrenceId(),
                        ocurrence.getStartOcurrenceLocalDateTime(),
                        ocurrence.getEndOcurrenceLocalDateTime(),
                        ocurrence.getObservation(),
                        ocurrence.getEmployee().getEmployeeName(),
                        ocurrence.getDepartment().getDepartmentName(),
                        ocurrence.getStopCodes(),
                        ocurrence.getAffectedEquipment(),
                        ocurrence.getProductionOrder().getProductionOrderNumber(),
                        ocurrence.getOcorrenceType(),
                        ocurrence.getStatus(),
                        ocurrence.getShift(),
                        ocurrence.getProductionOrder().getProductionLine().getProductionLineNumber(),
                        ocurrence.getProductionOrder().getProduct().getDescription()

              

        );
    }
}
 