package com.compoldata.induflow.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.compoldata.induflow.DTO.request.EmployeeForm;
import com.compoldata.induflow.exceptions.DepartmentNotFoundException;
import com.compoldata.induflow.model.Employee;
import com.compoldata.induflow.repository.appRepositories.DepartmentRepository;
import com.compoldata.induflow.repository.appRepositories.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    @Autowired
    private final PasswordEncoder passwordEncoder; 
    @Autowired
    private  final EmployeeRepository empRepository; 
   
    @Autowired 
    private  final DepartmentRepository departmentRepository; 
    //registra um novo funcionario no banco de dados
    public void registerEmployee(EmployeeForm employeeDTO){
        if (employeeDTO.name() == null) {
            throw new IllegalArgumentException("nome do usuário não pode ser nulo");
        }
        if (employeeDTO.email() == null) {
            throw new IllegalArgumentException("email do funcionario não pode ser nulo"); 
        }
        departmentRepository.findById(employeeDTO.department().getDepartmentId())
        .orElseThrow(DepartmentNotFoundException:: new);
        var passwordHash = passwordEncoder.encode(employeeDTO.password());
        Employee newEmployee = new Employee(); 
        newEmployee.setEmployeeName(employeeDTO.name());
        newEmployee.setEmployeeEmail(employeeDTO.email());
        newEmployee.setEmployeeDepartment(employeeDTO.department());
        newEmployee.setPassword(passwordHash);
        empRepository.save(newEmployee);
    }
    
    //retorna uma lista com todos os funcionarios cadastrados 
    public List<Employee> findAllEmployee(){
        return empRepository.findAll();
   
    }
   
    
}
