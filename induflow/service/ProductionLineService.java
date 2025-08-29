package com.compoldata.induflow.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import com.compoldata.induflow.model.ProductionLine;
import com.compoldata.induflow.model.ProductionOrder;
import com.compoldata.induflow.repository.appRepositories.ProductionLineRepository;
import com.compoldata.induflow.repository.appRepositories.ProductionOrderRepository;



@Service
public class ProductionLineService {

    @Autowired
    private ProductionLineRepository productionLineRepository;

    @Autowired
    private ProductionOrderRepository productionOrderRepository;

    //busca linha de produção 
    public ProductionLine getProductionLineById(Long id){
        return productionLineRepository.findById(id).
        orElseThrow(() -> new ResourceAccessException("linha de produção não existe"));
    }

    // inicia linha de produção
    @Transactional(transactionManager = "appTransactionManager")
    public void startLineProduction(Long productionLineId, Long productionOrderId) {
        // Busca a linha de produção pelo ID
        ProductionLine productionLine = productionLineRepository.findById(productionLineId)
                .orElseThrow(() -> new ResourceAccessException("linha de produção não existe"));

        // Busca a Ordem de Produção Pelo ID
        ProductionOrder productionOrder = productionOrderRepository.findById(productionOrderId)
                .orElseThrow(() -> new ResourceAccessException("Ordem De produção não Cadastrada"));

   
        // verifica se a linha de produção se o usuário está tentando associar a linha de produção na linha correta 
        if (!productionOrder.getProductionLine().equals(productionLine)) {
            throw new IllegalArgumentException("ordem de produção não está asociada a esta linha de produção.");
        }

        // altera a o estado da linha para rodando
        productionLine.setRunning(true);

        // salva no banco de dados as alterações na linha de produção
        productionLineRepository.save(productionLine);

    }

    // para linha de produção
    @Transactional(transactionManager = "appTransactionManager")
    public void stopLineProduction(Long productionLineId, Long productionOrderId) {

        // Busca a linha de produção pelo ID
        ProductionLine productionLine = productionLineRepository.findById(productionLineId)
                .orElseThrow(() -> new ResourceAccessException("linha de produção Não existe"));

        // Busca a Ordem de Produção Pelo ID
        ProductionOrder productionOrder = productionOrderRepository.findById(productionOrderId)
                .orElseThrow(() -> new ResourceAccessException("ordem de produção Não existe"));

        // altera o estado da linha de produção para parado!
        productionLine.setRunning(false);

        // captura a hora e data atual da finalização da linha
        productionOrder.setEndproductionDateTime(LocalDateTime.now());
  

        productionLineRepository.save(productionLine);
        productionOrderRepository.save(productionOrder);

    }



}
