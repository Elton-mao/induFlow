package com.compoldata.induflow.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.compoldata.induflow.DTO.request.DepartamentForm;
import com.compoldata.induflow.service.DepartmentService;

@Controller
public class DeparmentController {
    @Autowired
    private DepartmentService dService;

    // retorna o formulario de registro para o deparmento
    @GetMapping("/registerdeparment")
    public ModelAndView departmentRegister() {
        ModelAndView mView = new ModelAndView("register-deparment");
        mView.addObject("deparmentDTO", new DepartamentForm(null, null, null));
        return mView;
    }

    // registra o departamento
    @PostMapping("/registerdeparment")
    public String deparmentRegister(@ModelAttribute DepartamentForm deparmentRequest) {
        dService.departmentRegister(deparmentRequest);
        System.out.println(departmentRegister());
        return "index";
    }
    

}
