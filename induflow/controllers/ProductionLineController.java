package com.compoldata.induflow.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/productionline")
public class ProductionLineController {
    // @Autowired 
    // private ProductionLineService productionLineService;

    @GetMapping 
    public ModelAndView index(){
        return new ModelAndView("productionLine");
    }
    
}
