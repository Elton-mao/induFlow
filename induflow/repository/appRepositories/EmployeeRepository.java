package com.compoldata.induflow.repository.appRepositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.compoldata.induflow.model.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findByEmployeeEmail(String employeeEmail);
    
}
