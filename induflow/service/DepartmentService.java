package com.compoldata.induflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compoldata.induflow.DTO.request.DepartamentForm;
import com.compoldata.induflow.model.Department;
import com.compoldata.induflow.repository.appRepositories.DepartmentRepository;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository dRepository;

    public void departmentRegister(DepartamentForm departamentDTO){
        Department newDepartment = new Department(); 
        newDepartment.setDepartmentName(departamentDTO.name());
        dRepository.save(newDepartment);
    }
}
