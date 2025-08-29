package com.compoldata.induflow.DTO.request;

import java.time.LocalDateTime;

import com.compoldata.induflow.model.Department;
import com.compoldata.induflow.model.enums.AffectedEquipment;
import com.compoldata.induflow.model.enums.OccurrenceType;
import com.compoldata.induflow.model.enums.StopCodes;

public record OccurrenceForm(Long ocurrenceId,LocalDateTime startOcurrenceLocalDateTime, LocalDateTime endOcurrenceLocalDateTime,
        String Observation, Department department, StopCodes stopCodes,
        AffectedEquipment affectedEquipment, Long ProductionOrderId, OccurrenceType ocorrenceType) {

    // public static OccurrenceForm fromEntity(Ocurrence ocurrence) {
    //     return new OccurrenceForm(
    //             ocurrence.getOcurrenceId(),
    //             ocurrence.getStartOcurrenceLocalDateTime(),
    //             ocurrence.getEndOcurrenceLocalDateTime(),
    //             ocurrence.getObservation(),
    //             ocurrence.getDepartment(),
    //             ocurrence.getStopCodes(),
    //             ocurrence.getAffectedEquipment(),
    //             ocurrence.getEmployee().getEmployeeId(),
    //             ocurrence.getOcorrenceType()

    //     );

    // }
}
