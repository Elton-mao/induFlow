/**
 * Controlador REST para gerenciar ordens de produção.
 * Fornece endpoints para recuperar informações de produção e atualizar quantidades produzidas.
 * 
 * Endpoints:
 * - GET /getProducedAmount/{productionOrderId}: Recupera a quantidade produzida para uma determinada ordem de produção.
 * - GET /getProductionProgress/{productionOrderId}: Recupera a porcentagem de progresso da produção para uma determinada ordem de produção.
 * - GET /getProductionStatus/{productionOrderId}: Recupera o status da produção para uma determinada ordem de produção.
 * - POST /add/{productionOrderId}/{amount}: Adiciona uma quantidade produzida a uma determinada ordem de produção.
 * 
 * Dependências:
 * - ProductionOrderService: Serviço para lidar com operações de ordens de produção.
 * 
 * Exemplo de uso:
 * - Para obter a quantidade produzida: GET /getProducedAmount/1
 * - Para obter o progresso da produção: GET /getProductionProgress/1
 * - Para obter o status da produção: GET /getProductionStatus/1
 * - Para adicionar uma quantidade produzida: POST /add/1/100
 * 
 * Tratamento de erros:
 * - Retorna 404 NOT FOUND se a ordem de produção não for encontrada.
 * - Retorna 400 BAD REQUEST se houver um erro com os parâmetros da solicitação.
 * - Retorna 500 INTERNAL SERVER ERROR para erros inesperados.
 * 
 * Nota:
 * - Certifique-se de que o ID da ordem de produção e a quantidade sejam válidos e existam no sistema.
 * - A quantidade produzida e a quantidade total devem ser maiores que zero para evitar erros de divisão por zero.
 */
package com.compoldata.induflow.api;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compoldata.induflow.service.ProductionOrderService;
import com.compoldata.induflow.DTO.response.ToOcurrence;
import com.compoldata.induflow.DTO.response.ToProductionOrderLog;
import com.compoldata.induflow.integration.sigen.service.IntegrationSigenService;

@RestController
public class GetInformaTionProductionOrder {
    @Autowired
    private ProductionOrderService productionOrderService;
    @Autowired
    private IntegrationSigenService integrationSigenService;
    @GetMapping("/getProducedAmount/{productionOrderId}")
    public BigDecimal getProducedAmount(@PathVariable Long productionOrderId) {
        return productionOrderService.productionOrderInformation(productionOrderId).getProducedAmount();
    }

    @GetMapping("/getProductionProgress/{productionOrderId}")
    public double getProductionProgress(@PathVariable Long productionOrderId) {
        var productionOrder = productionOrderService.productionOrderInformation(productionOrderId);

        if (productionOrder.getAmount() == null || productionOrder.getProducedAmount() == null
                || productionOrder.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            return 0.0; // Evita divisão por zero
        }

        return productionOrder.getProducedAmount()
                .divide(productionOrder.getAmount(), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .doubleValue();
    }

    // ajax status da produção
    @GetMapping("/getProductionStatus/{productionOrderId}")
    public ResponseEntity<String> getProductionStatus(@PathVariable Long productionOrderId) {
        var productionOrder = productionOrderService.productionOrderInformation(productionOrderId);

        if (productionOrder != null) {
            return ResponseEntity.ok(productionOrder.getStatus().toString());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status desconhecido");
        }
    }

    /**
     * Adds a produced amount to a given production order.
     *
     * @param productionOrderId the ID of the production order
     * @param amount            the amount to be added
     * @return a ResponseEntity indicating the result of the operation
     */
    @PostMapping("/add/{productionOrderId}/{amount}")
    public ResponseEntity<String> addProducedAmount(
            @PathVariable Long productionOrderId,
            @PathVariable BigDecimal amount) {
        try {
            productionOrderService.addProducedAmount(productionOrderId, amount);
            return ResponseEntity.ok("Quantidade adicionada com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro inesperado: " + e.getMessage());
        }
    }
     @GetMapping("/{productionOrderId}")
    public ResponseEntity<List<ToProductionOrderLog>> getLogs(@PathVariable Long productionOrderId) {
        var productionOrder = productionOrderService.productionOrderInformation(productionOrderId);
        
        List<ToProductionOrderLog> logs = productionOrder.getProductionOrderLogs().stream()
                .map(ToProductionOrderLog::toProductionOrderLog)
                .toList();
        
        return ResponseEntity.ok(logs);
    }
    @GetMapping("atualizar")
    public String teste() {
        integrationSigenService.getProductionOrderSigen();
        return "foi";
    }

    
    /**
     * Recupera uma lista de ocorrências para uma determinada ordem de produção.
     *
     * @param productionOrderId o ID da ordem de produção
     * @return uma lista de objetos ToOcurrence associados à ordem de produção especificada
     */
    @GetMapping("/listForProductionOrder/{productionOrderId}")
    public List<ToOcurrence> listOccurrencesForProductionOrder(@PathVariable Long productionOrderId) { 
        return productionOrderService.listOccurrencesForProductionOrder(productionOrderId);
    }
}
