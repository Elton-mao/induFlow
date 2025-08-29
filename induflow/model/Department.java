package com.compoldata.induflow.model;

import java.util.List;

import com.compoldata.induflow.DTO.request.DepartamentForm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "departmentId")
public class Department {
    @Id
    @Column(name = "departmentId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId; 
    @Column(unique = true,nullable = false)
    private String departmentName; 
    @OneToMany(mappedBy = "employeeDepartment")
    private List<Employee> employeesList; 
    
    public Department (DepartamentForm departamentDTO){
        this.departmentName = departamentDTO.name();
        this.employeesList = departamentDTO.employees();
    }
}
