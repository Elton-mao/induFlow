package com.compoldata.induflow.DTO.request;

import java.math.BigDecimal;

import com.compoldata.induflow.DTO.response.ToExternalProductionOrder;
import com.compoldata.induflow.integration.sigen.model.ExternalProductionOrder;

public record ExternalProductionOrderForm(
        String productionOrderNumber,
        String materialCode,
        String productionOrderStatus,
        BigDecimal amount,
        String description) {

        public static ExternalProductionOrder fromExternalProductionOrder(ToExternalProductionOrder toExternalProductionOrder){
        return new ExternalProductionOrder( 
                toExternalProductionOrder.productionOrderNumber(),
                toExternalProductionOrder.materialCode(),
                toExternalProductionOrder.productionOrderStatus(),
                toExternalProductionOrder.amount(),
                toExternalProductionOrder.description()
                );
        }  
}
