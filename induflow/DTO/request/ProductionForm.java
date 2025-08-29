package com.compoldata.induflow.DTO.request;

import java.time.LocalDateTime;
import java.util.List;

import com.compoldata.induflow.model.Client;
import com.compoldata.induflow.model.Ocurrence;
import com.compoldata.induflow.model.Product;
import com.compoldata.induflow.model.ProductionLine;

public record ProductionForm(Long productionOrderId,String productionOrderNumber,double amount,LocalDateTime  startProductionDateTime,
LocalDateTime  endproductionDateTime,Product product,List<Ocurrence> ocurrences,
Client client,ProductionLine productionLine, boolean isOpen) {
    
}
