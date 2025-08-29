package com.compoldata.induflow.model;

import java.util.List;

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
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@ToString
public class ProductionLine {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productionLineId;

    private boolean isRunning = false; 

    @Column(unique = true)
    private Integer productionLineNumber; 

    @OneToMany(mappedBy = "productionLine")
    private List<ProductionOrder> productionOrders; 
     
    
}