package com.compoldata.induflow.DTO.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.compoldata.induflow.model.ProductionOrderLog;

public record ToProductionOrderLog(
    Long productionOrderLogId,
    String productionOrderNumber,
    String productCode,
    Integer productionLineNumber,
    LocalDateTime recordedTime,
    LocalDateTime startProductionOrder,
    String logmessage,
    BigDecimal amount,
    BigDecimal producedAmount,
    String employeeName
) {
    public static ToProductionOrderLog toProductionOrderLog(ProductionOrderLog log){
        return new ToProductionOrderLog(
            log.getProductionOrderLogId(),
            log.getProductionOrder().getProductionOrderNumber(),
            log.getProductionOrder().getProduct().getProductCode(),
            log.getProductionOrder().getProductionLine().getProductionLineNumber(),
            log.getRecordedTime(),
            log.getProductionOrder().getStartProductionDateTime(),
            log.getLogmessage(),
            log.getProductionOrder().getAmount(),
            log.getProducedAmount(),
            log.getEmployeeName()

        );    
    }
}