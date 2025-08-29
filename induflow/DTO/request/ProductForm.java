package com.compoldata.induflow.DTO.request;

import java.util.List;

import com.compoldata.induflow.model.ProductionOrder;

public record ProductForm(String description, String productCode,List<ProductionOrder> listProductionOrders) {
    
}
