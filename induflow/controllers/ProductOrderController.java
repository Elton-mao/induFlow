package com.compoldata.induflow.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.compoldata.induflow.model.ProductionOrder;
import com.compoldata.induflow.model.enums.ProductionOrderStatus;
import com.compoldata.induflow.service.ProductionOrderService;

@Controller
public class ProductOrderController {
    
    @Autowired
    private ProductionOrderService productionOrderService;

    // Lista todos as ordens de produção que estão abertas
    @GetMapping("/openproductionorders")
    public ModelAndView listAllOpenProductionOrder() {
        ModelAndView mView = new ModelAndView("start-production");
        mView.addObject("productionOrders", productionOrderService.listAllOpenProductionOrders());
        return mView;
    }

    // lista todos as ordens de produção que estão em andamento
    @GetMapping("/isruningproductionorders")
    public ModelAndView listAllIsRuningProductionOrder() {
        List<ProductionOrderStatus> status = List.of(ProductionOrderStatus.PARADA,ProductionOrderStatus.SETUP,ProductionOrderStatus.PRODUZINDO);
        ModelAndView mView = new ModelAndView("is-runnig-production");
        mView.addObject("productionOrders", productionOrderService.getActiveOrPendingProductionOrders(status));
        return mView;
    }

    // lista todas as ordens de produção concluidas
    @GetMapping("/closeproductionorders")
    public ModelAndView listAllCloseProductionOrder() {
        ModelAndView mView = new ModelAndView("production-order-close-list");
        mView.addObject("productionOrders", productionOrderService.listAllCloseProductionOrders());
        return mView;
    }

    //returna todos os setups ativos
    @GetMapping("issetupproductionorders")
    public ModelAndView listAllIsSetupProductionOrder() {
        var model = Map.of("productionOrders", productionOrderService.listAllIsSetupProductionOrders());
        return new ModelAndView("setup-list",model);
    }

    // dashbord de linha de produção
    @GetMapping("/dashboard/{productionOrderId}")
    public ModelAndView productionOrderInformation(@PathVariable Long productionOrderId) {
        ProductionOrder productionOrder = productionOrderService.productionOrderInformation(productionOrderId);
        ModelAndView mView = new ModelAndView("dashboard");
        mView.addObject("log", productionOrderService.getLogs(productionOrderId));
        mView.addObject("ocurrences", productionOrderService.listOccurrencesForProductionOrder(productionOrderId));
        mView.addObject("productionOrder", productionOrder);
        return mView;    
    }

    // painel de controle da linha de produção
    @GetMapping("/controlproductionOrder/{productionOrderId}")
    public ModelAndView controlProductionOrder(@PathVariable Long productionOrderId) {
        ModelAndView mView = new ModelAndView("control-production-order");
        ProductionOrder productionOrder = productionOrderService.productionOrderInformation(productionOrderId);
        mView.addObject("productionOrder", productionOrder);
        return mView;
    }

    //finaliza a ordem de produção
    @PostMapping("/endproduction/{productionOrderId}")
    public String endProductionOrder(@PathVariable Long productionOrderId) {
        productionOrderService.endProductionOrder(productionOrderId);
        return "redirect:/productionorderdetails/"+productionOrderId;
        
    }

    //exibi detalhes da ordem de Produção
    @GetMapping("/productionorderdetails/{productionOrderId}")
    public ModelAndView productionOrderDetails(@PathVariable Long productionOrderId) {
        var model = productionOrderService.productionOrderInformation(productionOrderId);
        return new ModelAndView("production-order-details", "productionOrder", model);
    }
    

}
