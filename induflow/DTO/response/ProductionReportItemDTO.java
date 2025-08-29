package com.compoldata.induflow.DTO.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.compoldata.induflow.model.enums.AffectedEquipment;
import com.compoldata.induflow.model.enums.OccurrenceStatus;
import com.compoldata.induflow.model.enums.OccurrenceType;
import com.compoldata.induflow.model.enums.Shift;
import com.compoldata.induflow.model.enums.StopCodes;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductionReportItemDTO {

    // Comuns
    private String type; // "LOG" ou "OCURRENCE"
    private LocalDateTime dateTime;

    // Log fields
    private String logMessage;
    private BigDecimal producedAmount;
    private String employeeName;

    // Occurrence fields
    private String observation;
    private String employee;
    private String department;
    private StopCodes stopCode;
    private AffectedEquipment affectedEquipment;
    private OccurrenceStatus status;
    private OccurrenceType occurrenceType;
    private Shift shift;
}
