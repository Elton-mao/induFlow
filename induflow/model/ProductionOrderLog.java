package com.compoldata.induflow.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "Productionorderlog")
public class ProductionOrderLog {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long productionOrderLogId;

        @ManyToOne
        @JoinColumn(name = "production_order_id", nullable = false)
        private ProductionOrder productionOrder;

        @Column(nullable = false)
        private LocalDateTime recordedTime ;

        @Column(nullable = false)
        private BigDecimal producedAmount;

        @Column(nullable = false)
        private String logmessage;
        
        @Column(nullable=false)
        private String employeeName; 


    }

