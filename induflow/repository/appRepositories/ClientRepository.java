package com.compoldata.induflow.repository.appRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.compoldata.induflow.model.Client;




@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Add custom query methods if needed
}