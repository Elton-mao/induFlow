package com.compoldata.induflow.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.compoldata.induflow.service.AutheticationService;
import com.compoldata.induflow.service.ProductionOrderService;
import com.compoldata.induflow.service.ProductionReportService;

@Controller
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private ProductionOrderService productionorderservice;

    @Autowired
    private AutheticationService autheticationService;

    @Autowired
    private ProductionReportService productionReportService;

    @GetMapping("/{productionOrderId}")
    public ModelAndView fechamentodeOp(@PathVariable Long productionOrderId) {
      var productionOrder = productionorderservice.productionOrderInformation(productionOrderId);
      var reportItems = productionReportService.buildReportItems(productionOrder);
      var employee = autheticationService.getAutheticatedUser(); 
      var model = Map.of("productionOrder", productionOrder,
       "reportItems", reportItems
       , "employee", employee);
      return new ModelAndView("relatoriodefechamentodeop", model);
    }

}