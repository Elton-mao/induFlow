package com.compoldata.induflow.repository.appRepositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.compoldata.induflow.model.ProductionLine;

@Repository
public interface ProductionLineRepository extends JpaRepository<ProductionLine,Long>{
        Optional<ProductionLine> findByProductionLineNumber(Integer productionLineNumber);
}
