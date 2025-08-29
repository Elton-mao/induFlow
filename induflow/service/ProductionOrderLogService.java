package com.compoldata.induflow.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compoldata.induflow.model.ProductionOrder;
import com.compoldata.induflow.model.ProductionOrderLog;
import com.compoldata.induflow.repository.appRepositories.ProductionOrderLogRepository;
@Service
public class ProductionOrderLogService {
    @Autowired
    private ProductionOrderLogRepository productionOrderLogRepository; 

    //registra o log de entrada de materias 
    public ProductionOrderLog registerProductionOrderLog(ProductionOrder productionOrder,String logmessage,BigDecimal producedAmount , String employeeName ) {
        var log =  ProductionOrderLog.builder()
        .logmessage(logmessage)
        .productionOrder(productionOrder)
        .producedAmount(producedAmount)
        .recordedTime(LocalDateTime.now())
        .employeeName(employeeName)
        .build();
        return productionOrderLogRepository.save(log);
    }
}
