package com.compoldata.induflow.model.enums;

public enum ProductionOrderStatus {
        PENDENTE,      // Ordem cadastrada no sistema, mas não está rodando
        PRODUZINDO,    // Ordem em execução
        SETUP,    // Ordem em preparação 
        FINALIZADA,     // Ordem finalizada
        PARADA // OCORRENCIA 
    
}
