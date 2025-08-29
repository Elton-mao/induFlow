package com.compoldata.induflow.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.compoldata.induflow.DTO.request.EmployeeForm;
import com.compoldata.induflow.repository.appRepositories.DepartmentRepository;
import com.compoldata.induflow.service.EmployeeService;

@Controller
public class EmployeeController {
    @Autowired 
    private EmployeeService employeeService; 

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/registeremployee")
    public ModelAndView registerEmployee(){
        ModelAndView mView = new ModelAndView("/register-employee");
        mView.addObject("employee",new EmployeeForm(null, null, null, null));
        mView.addObject("departments", departmentRepository.findAll());   
        return mView; 
    }

    @PostMapping("/registeremployee")
    public String registerEmployee(@ModelAttribute EmployeeForm employeeRequest){
        employeeService.registerEmployee(employeeRequest);
        return "redirect:/registeremployee";
        
    }

    
}
