package com.compoldata.induflow.DTO.request;

import com.compoldata.induflow.model.Department;

public record EmployeeForm(String name,String email, Department department,String password) {
    
}
