package com.compoldata.induflow.DTO.request;

import com.compoldata.induflow.model.ProductionOrder;

public record ProductLineForm(Long productionLineId, int productionLineNumber,ProductionOrder productionOrder) {
    
}
