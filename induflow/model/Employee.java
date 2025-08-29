package com.compoldata.induflow.model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = "listOcurrences") 
@EqualsAndHashCode
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long EmployeeId; 
    @Column(nullable = false)
    private String employeeName;
    @Column(nullable = false,unique = true)
    private String employeeEmail; 
    @ManyToOne
    @JoinColumn(name = "employee_department_id",nullable = false)
    private Department employeeDepartment; 
    @OneToMany(mappedBy="employee")
    private List<Ocurrence> listOcurrences; 
    private String password;
    
}