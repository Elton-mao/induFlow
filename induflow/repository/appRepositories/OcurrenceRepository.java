package com.compoldata.induflow.repository.appRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.compoldata.induflow.model.Ocurrence;
@Repository
public interface OcurrenceRepository extends JpaRepository<Ocurrence,Long>{
    
}
