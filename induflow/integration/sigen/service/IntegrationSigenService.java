package com.compoldata.induflow.integration.sigen.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.compoldata.induflow.DTO.response.ToExternalProductionOrder;
import com.compoldata.induflow.exceptions.ExternalProductionOrderNotFoundException;
import com.compoldata.induflow.integration.sigen.model.ExternalProductionOrder;
import com.compoldata.induflow.repository.sigenRepositories.ExternalProductionOrderRepository;
import com.compoldata.induflow.service.ProductionOrderService;

@Service
public class IntegrationSigenService {
    
    @Autowired
    private ExternalProductionOrderRepository externalProductionOrderRepository;

    @Autowired ProductionOrderService productionOrderService; 

    /**
     * Método agendado para buscar e processar ordens de produção do sistema externo.
     * Este método é executado a uma taxa fixa de 5 minutos (300000 milissegundos).
     * Ele recupera todas as ordens de produção externas com o status "Em Aberto",
     * registra-as no serviço de ordens de produção e imprime uma mensagem de confirmação
     * no console indicando que o banco de dados foi atualizado.
     */
    // @Scheduled(fixedRate = 300000)
    public void getProductionOrderSigen() {
        List<ExternalProductionOrder> externalOrders =  externalProductionOrderRepository.findAll()
        .stream().filter(order -> "Em Aberto                                                   ".equalsIgnoreCase(order.getProductionOrderStatus()))
        .collect(Collectors.toList());
        externalOrders.forEach(productionOrderService::registerProductionOrder);
        System.out.println("banco de dados atualizado");    
    }

    /**
     * Recupera uma ExternalProductionOrder pelo número da ordem de produção.
     *
     * @param productionOrderNumber o número da ordem de produção a ser pesquisado
     * @return a ExternalProductionOrder associada ao número da ordem de produção fornecido
     * @throws IllegalArgumentException se nenhuma ExternalProductionOrder for encontrada com o número da ordem de produção fornecido
     */
    public ToExternalProductionOrder getExternalProductionOrderByNumber(String productionOrderNumber){ 
        return externalProductionOrderRepository.findByProductionOrderNumber(productionOrderNumber)
        .map(ToExternalProductionOrder::toExternalProductionOrder)
        .orElseThrow(() -> new ExternalProductionOrderNotFoundException("Numero da Ordem de Produção não Existe no Sistema :" + productionOrderNumber));
    }

}