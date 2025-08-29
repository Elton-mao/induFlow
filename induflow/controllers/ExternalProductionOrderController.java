package com.compoldata.induflow.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.compoldata.induflow.DTO.request.ExternalProductionOrderForm;
import com.compoldata.induflow.DTO.response.ToExternalProductionOrder;
import com.compoldata.induflow.integration.sigen.service.IntegrationSigenService;
import com.compoldata.induflow.service.ProductionOrderService;

@Controller
@RequestMapping("/sigen")
public class ExternalProductionOrderController {

    @Autowired
    private IntegrationSigenService integrationSigenService; 
    
    @Autowired
    private ProductionOrderService productionOrderService; 
    @GetMapping("/search")
    public ModelAndView searchProductionOrder(){
        return new ModelAndView("search-productionorder");
    }

    @GetMapping("/productionOrder/{productionOrderNumber}")
    public ModelAndView findProductionOrder(@PathVariable String productionOrderNumber ){
        var model = Map.of("productionOrder",
        integrationSigenService.getExternalProductionOrderByNumber(productionOrderNumber));
        return new ModelAndView("production-order-external",model);
    }
    
    @PostMapping("/producionOrder")
    public String transferProductionOrder(@ModelAttribute ToExternalProductionOrder toExternalProductionOrder){
        var producionOrder = ExternalProductionOrderForm.fromExternalProductionOrder(toExternalProductionOrder); 
        System.out.println(toExternalProductionOrder.toString());
        productionOrderService.registerProductionOrder(producionOrder);
        return "redirect:/openproductionorders";
        
    }


    

    
    
}
                                  