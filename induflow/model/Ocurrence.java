package com.compoldata.induflow.model;

import java.time.LocalDateTime;

import com.compoldata.induflow.model.enums.AffectedEquipment;
import com.compoldata.induflow.model.enums.OccurrenceType;
import com.compoldata.induflow.model.enums.Shift;
import com.compoldata.induflow.model.enums.OccurrenceStatus;
import com.compoldata.induflow.model.enums.StopCodes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class Ocurrence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long OcurrenceId;

    @Column(nullable = false)
    private LocalDateTime startOcurrenceLocalDateTime;

    private LocalDateTime endOcurrenceLocalDateTime;
    private String Observation;

    @ManyToOne
    @JoinColumn(name = "employee_ocurrence_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Enumerated(EnumType.STRING)
    private StopCodes stopCodes;

    @Enumerated(EnumType.STRING)
    private AffectedEquipment affectedEquipment;

    @ManyToOne
    @JoinColumn(name = "production_order_id")
    private ProductionOrder productionOrder;

    private OccurrenceStatus status;  
    
    
    private OccurrenceType ocorrenceType; 
    
    @Enumerated(EnumType.STRING)
    private Shift shift; 
    
}
