package com.compoldata.induflow.DTO.request;

import java.util.List;

import com.compoldata.induflow.model.Department;
import com.compoldata.induflow.model.Employee;

/**
 * DepartamentDTO
 */
public record DepartamentForm(String name, Department department, List<Employee> employees) {
    
}

