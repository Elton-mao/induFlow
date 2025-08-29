package com.compoldata.induflow.DTO.request;

import com.compoldata.induflow.model.ProductionOrder;

public record ClientForm(String corporateReason, ProductionOrder productionOrder) {
    
}
