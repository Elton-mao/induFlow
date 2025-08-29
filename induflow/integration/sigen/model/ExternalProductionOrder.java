package com.compoldata.induflow.integration.sigen.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cp_ordensprod")
public class ExternalProductionOrder { 
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)    
    // @Column(name = "ID")
    // private Long ExternalProductionOrderId;
    @Id
    @Column(name = "NumOrdem")
    private String productionOrderNumber;

    @Column(name = "CodMaterial")
    private String materialCode;

    @Column(name = "Texto_SitOrdem")
    private String productionOrderStatus;

    @Column(name = "QuantOrdem")
    private  BigDecimal amount;

    @Column(name = "DescMaterial")
    private String description;
}
