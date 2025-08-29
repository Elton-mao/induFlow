package com.compoldata.induflow.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.compoldata.induflow.model.enums.ProductionOrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Data
public class ProductionOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productionOrderId;

    @Column(nullable = false, unique = true)
    private String productionOrderNumber;

    @Column(nullable = false)
    private BigDecimal amount;

    private LocalDateTime startProductionDateTime;
    
    private LocalDateTime endproductionDateTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "production_id", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "productionOrder" ,orphanRemoval = true)
    private List<Ocurrence> ocurrences;

    @ManyToOne
    private Client client;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "production_line_id", referencedColumnName = "productionLineId")
    private ProductionLine productionLine;

    @Column(nullable = true)
    private BigDecimal producedAmount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private ProductionOrderStatus status;

    @OneToMany(mappedBy = "productionOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductionOrderLog> productionOrderLogs;

   
}
